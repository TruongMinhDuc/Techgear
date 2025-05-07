package com.techgear.techgear_be.utils;

import com.techgear.techgear_be.models.BaseEntityAudit;
import com.techgear.techgear_be.models.address.District;
import com.techgear.techgear_be.models.address.Province;
import com.techgear.techgear_be.models.address.Ward;
import com.techgear.techgear_be.models.authentication.User;
import com.techgear.techgear_be.models.chat.Room;
import com.techgear.techgear_be.models.customer.Customer;
import com.techgear.techgear_be.models.customer.CustomerGroup;
import com.techgear.techgear_be.models.customer.CustomerResource;
import com.techgear.techgear_be.models.customer.CustomerStatus;
import com.techgear.techgear_be.models.employee.*;
import com.techgear.techgear_be.models.inventory.*;
import com.techgear.techgear_be.models.order.Order;
import com.techgear.techgear_be.models.order.OrderCancellationReason;
import com.techgear.techgear_be.models.order.OrderResource;
import com.techgear.techgear_be.models.order.OrderVariantKey;
import com.techgear.techgear_be.models.product.*;
import com.techgear.techgear_be.repositories.address.DistrictRepository;
import com.techgear.techgear_be.repositories.address.ProvinceRepository;
import com.techgear.techgear_be.repositories.address.WardRepository;
import com.techgear.techgear_be.repositories.authentication.RoleRepository;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.repositories.product.ProductRepository;
import com.techgear.techgear_be.repositories.product.TagRepository;
import com.techgear.techgear_be.repositories.product.VariantRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperUtils {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private VariantRepository variantRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private WardRepository wardRepository;
    @Autowired
    private UserRepository userRepository;

    public Province mapToProvince(@Nullable Long id) {
        return id == null ? null : provinceRepository.getById(id);
    }

    public District mapToDistrict(@Nullable Long id) {
        return id == null ? null : districtRepository.getById(id);
    }

    public Ward mapToWard(@Nullable Long id) {
        return id == null ? null : wardRepository.getById(id);
    }

    public abstract Office mapToOffice(Long id);

    public abstract Department mapToDepartment(Long id);

    public abstract JobType mapToJobType(Long id);

    public abstract JobLevel mapToJobLevel(Long id);

    public abstract JobTitle mapToJobTitle(Long id);

    public abstract CustomerGroup mapToCustomerGroup(Long id);

    public abstract CustomerResource mapToCustomerResource(Long id);

    public abstract CustomerStatus mapToCustomerStatus(Long id);

    public abstract Category mapToCategory(Long id);

    public abstract Brand mapToBrand(Long id);

    public abstract Supplier mapToSupplier(Long id);

    public abstract Unit mapToUnit(Long id);

    public abstract Guarantee mapToGuarantee(Long id);

    public abstract Warehouse mapToWarehouse(Long id);

    public abstract DocketReason mapToDocketReason(Long id);

    public abstract Destination mapToDestination(Long id);

    public abstract PurchaseOrder mapToPurchaseOrder(Long id);

    public abstract OrderResource mapToOrderResource(Long id);

    public abstract OrderCancellationReason mapToOrderCancellationReason(Long id);

    public abstract Customer mapToCustomer(Long id);

    public abstract Order mapToOrder(Long id);

    public abstract Room mapToRoom(Long id);

    public Variant mapToVariant(Long id) {
        return variantRepository.getById(id);
    }

    public Product mapToProduct(Long id) {
        return productRepository.getById(id);
    }

    public User mapToUser(Long id) {
        return userRepository.getById(id);
    }

    @Named("hashPassword")
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @AfterMapping
    @Named("attachUser")
    public User attachUser(@MappingTarget User user) {
        return user.setRoles(attachSet(user.getRoles(), roleRepository));
    }

    @AfterMapping
    @Named("attachProduct")
    public Product attachProduct(@MappingTarget Product product) {
        product.getImages().forEach(image -> image.setProduct(product));
        product.setTags(attachSet(product.getTags(), tagRepository));
        product.getVariants().forEach(variant -> variant.setProduct(product));
        return product;
    }

    @AfterMapping
    @Named("attachCount")
    public Count attachCount(@MappingTarget Count count) {
        count.getCountVariants().forEach(countVariant -> {
            countVariant.setCountVariantKey(new CountVariantKey(count.getId(), countVariant.getVariant().getId()));
            countVariant.setCount(count);
        });
        return count;
    }

    @AfterMapping
    @Named("attachOrder")
    public Order attachOrder(@MappingTarget Order order) {
        order.getOrderVariants().forEach(orderVariant -> {
            orderVariant.setOrderVariantKey(new OrderVariantKey(order.getId(), orderVariant.getVariant().getId()));
            orderVariant.setOrder(order);
        });
        return order;
    }

    @AfterMapping
    @Named("attachDocket")
    public Docket attachDocket(@MappingTarget Docket docket) {
        docket.getDocketVariants().forEach(docketVariant -> {
            docketVariant.setDocketVariantKey(new DocketVariantKey(docket.getId(), docketVariant.getVariant().getId()));
            docketVariant.setDocket(docket);
        });
        return docket;
    }

    @AfterMapping
    @Named("attachPurchaseOrder")
    public PurchaseOrder attachPurchaseOrder(@MappingTarget PurchaseOrder purchaseOrder) {
        purchaseOrder.getPurchaseOrderVariants().forEach(purchaseOrderVariant -> {
            purchaseOrderVariant.setPurchaseOrderVariantKey(
                    new PurchaseOrderVariantKey(purchaseOrder.getId(), purchaseOrderVariant.getVariant().getId()));
            purchaseOrderVariant.setPurchaseOrder(purchaseOrder);
        });
        return purchaseOrder;
    }

    private <E extends BaseEntityAudit> Set<E> attachSet(Set<E> entities, JpaRepository<E, Long> repository) {
        Set<E> detachedSet = Optional.ofNullable(entities).orElseGet(HashSet::new);
        Set<E> attachedSet = new HashSet<>();

        for (E entity : detachedSet) {
            if (entity.getId() != null) {
                repository.findById(entity.getId()).ifPresent(attachedSet::add);
            } else {
                attachedSet.add(entity);
            }
        }

        return attachedSet;
    }

}
