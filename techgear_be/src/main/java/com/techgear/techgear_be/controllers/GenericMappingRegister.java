package com.techgear.techgear_be.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.constants.SearchFieldsConst;
import com.techgear.techgear_be.dtos.address.AddressRequest;
import com.techgear.techgear_be.dtos.address.AddressResponse;
import com.techgear.techgear_be.dtos.address.district.DistrictRequest;
import com.techgear.techgear_be.dtos.address.district.DistrictResponse;
import com.techgear.techgear_be.dtos.address.province.ProvinceRequest;
import com.techgear.techgear_be.dtos.address.province.ProvinceResponse;
import com.techgear.techgear_be.dtos.address.ward.WardRequest;
import com.techgear.techgear_be.dtos.address.ward.WardResponse;
import com.techgear.techgear_be.dtos.authentication.role.RoleRequest;
import com.techgear.techgear_be.dtos.authentication.role.RoleResponse;
import com.techgear.techgear_be.dtos.authentication.user.UserRequest;
import com.techgear.techgear_be.dtos.authentication.user.UserResponse;
import com.techgear.techgear_be.dtos.cashbook.PaymentMethodRequest;
import com.techgear.techgear_be.dtos.cashbook.PaymentMethodResponse;
import com.techgear.techgear_be.dtos.chat.RoomRequest;
import com.techgear.techgear_be.dtos.chat.RoomResponse;
import com.techgear.techgear_be.dtos.customer.*;
import com.techgear.techgear_be.dtos.employee.*;
import com.techgear.techgear_be.dtos.general.ImageRequest;
import com.techgear.techgear_be.dtos.general.ImageResponse;
import com.techgear.techgear_be.dtos.inventory.*;
import com.techgear.techgear_be.dtos.order.*;
import com.techgear.techgear_be.dtos.product.*;
import com.techgear.techgear_be.dtos.promotion.PromotionRequest;
import com.techgear.techgear_be.dtos.promotion.PromotionResponse;
import com.techgear.techgear_be.dtos.review.ReviewRequest;
import com.techgear.techgear_be.dtos.review.ReviewResponse;
import com.techgear.techgear_be.dtos.reward.RewardStrategyRequest;
import com.techgear.techgear_be.dtos.reward.RewardStrategyResponse;
import com.techgear.techgear_be.dtos.waybill.WaybillRequest;
import com.techgear.techgear_be.dtos.waybill.WaybillResponse;
import com.techgear.techgear_be.models.address.Address;
import com.techgear.techgear_be.models.address.District;
import com.techgear.techgear_be.models.address.Ward;
import com.techgear.techgear_be.models.authentication.Role;
import com.techgear.techgear_be.models.authentication.User;
import com.techgear.techgear_be.models.cashbook.PaymentMethod;
import com.techgear.techgear_be.models.chat.Room;
import com.techgear.techgear_be.models.customer.Customer;
import com.techgear.techgear_be.models.customer.CustomerGroup;
import com.techgear.techgear_be.models.customer.CustomerResource;
import com.techgear.techgear_be.models.customer.CustomerStatus;
import com.techgear.techgear_be.models.employee.*;
import com.techgear.techgear_be.models.general.Image;
import com.techgear.techgear_be.models.inventory.*;
import com.techgear.techgear_be.models.order.Order;
import com.techgear.techgear_be.models.order.OrderCancellationReason;
import com.techgear.techgear_be.models.order.OrderResource;
import com.techgear.techgear_be.models.product.*;
import com.techgear.techgear_be.models.reward.RewardStrategy;
import com.techgear.techgear_be.mappers.address.AddressMapper;
import com.techgear.techgear_be.mappers.address.DistrictMapper;
import com.techgear.techgear_be.mappers.address.WardMapper;
import com.techgear.techgear_be.mappers.authentication.RoleMapper;
import com.techgear.techgear_be.mappers.authentication.UserMapper;
import com.techgear.techgear_be.mappers.cashbook.PaymentMethodMapper;
import com.techgear.techgear_be.mappers.chat.RoomMapper;
import com.techgear.techgear_be.mappers.customer.CustomerGroupMapper;
import com.techgear.techgear_be.mappers.customer.CustomerMapper;
import com.techgear.techgear_be.mappers.customer.CustomerResourceMapper;
import com.techgear.techgear_be.mappers.customer.CustomerStatusMapper;
import com.techgear.techgear_be.mappers.employee.*;
import com.techgear.techgear_be.mappers.general.ImageMapper;
import com.techgear.techgear_be.mappers.inventory.*;
import com.techgear.techgear_be.mappers.order.OrderCancellationReasonMapper;
import com.techgear.techgear_be.mappers.order.OrderMapper;
import com.techgear.techgear_be.mappers.order.OrderResourceMapper;
import com.techgear.techgear_be.mappers.product.*;
import com.techgear.techgear_be.mappers.reward.RewardStrategyMapper;
import com.techgear.techgear_be.repositories.address.AddressRepository;
import com.techgear.techgear_be.repositories.address.DistrictRepository;
import com.techgear.techgear_be.repositories.address.WardRepository;
import com.techgear.techgear_be.repositories.authentication.RoleRepository;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.repositories.cashbook.PaymentMethodRepository;
import com.techgear.techgear_be.repositories.chat.RoomRepository;
import com.techgear.techgear_be.repositories.customer.CustomerGroupRepository;
import com.techgear.techgear_be.repositories.customer.CustomerRepository;
import com.techgear.techgear_be.repositories.customer.CustomerResourceRepository;
import com.techgear.techgear_be.repositories.customer.CustomerStatusRepository;
import com.techgear.techgear_be.repositories.employee.*;
import com.techgear.techgear_be.repositories.general.ImageRepository;
import com.techgear.techgear_be.repositories.inventory.*;
import com.techgear.techgear_be.repositories.order.OrderCancellationReasonRepository;
import com.techgear.techgear_be.repositories.order.OrderRepository;
import com.techgear.techgear_be.repositories.order.OrderResourceRepository;
import com.techgear.techgear_be.repositories.product.*;
import com.techgear.techgear_be.repositories.reward.RewardStrategyRepository;
import com.techgear.techgear_be.services.CrudService;
import com.techgear.techgear_be.services.GenericService;
import com.techgear.techgear_be.services.address.ProvinceService;
import com.techgear.techgear_be.services.inventory.DocketService;
import com.techgear.techgear_be.services.promotion.PromotionService;
import com.techgear.techgear_be.services.review.ReviewService;
import com.techgear.techgear_be.services.waybill.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@AllArgsConstructor
public class GenericMappingRegister {

    private ApplicationContext context;
    private RequestMappingHandlerMapping handlerMapping;

    private GenericController<ProvinceRequest, ProvinceResponse> provinceController;
    private GenericController<DistrictRequest, DistrictResponse> districtController;
    private GenericController<WardRequest, WardResponse> wardController;
    private GenericController<AddressRequest, AddressResponse> addressController;
    private GenericController<UserRequest, UserResponse> userController;
    private GenericController<RoleRequest, RoleResponse> roleController;
    private GenericController<OfficeRequest, OfficeResponse> officeController;
    private GenericController<DepartmentRequest, DepartmentResponse> departmentController;
    private GenericController<JobLevelRequest, JobLevelResponse> jobLevelController;
    private GenericController<JobTypeRequest, JobTypeResponse> jobTypeController;
    private GenericController<JobTitleRequest, JobTitleResponse> jobTitleController;
    private GenericController<EmployeeRequest, EmployeeResponse> employeeController;
    private GenericController<CustomerGroupRequest, CustomerGroupResponse> customerGroupController;
    private GenericController<CustomerResourceRequest, CustomerResourceResponse> customerResourceController;
    private GenericController<CustomerStatusRequest, CustomerStatusResponse> customerStatusController;
    private GenericController<CustomerRequest, CustomerResponse> customerController;
    private GenericController<PropertyRequest, PropertyResponse> propertyController;
    private GenericController<CategoryRequest, CategoryResponse> categoryController;
    private GenericController<TagRequest, TagResponse> tagController;
    private GenericController<GuaranteeRequest, GuaranteeResponse> guaranteeController;
    private GenericController<UnitRequest, UnitResponse> unitController;
    private GenericController<SupplierRequest, SupplierResponse> supplierController;
    private GenericController<BrandRequest, BrandResponse> brandController;
    private GenericController<SpecificationRequest, SpecificationResponse> specificationController;
    private GenericController<ProductRequest, ProductResponse> productController;
    private GenericController<VariantRequest, VariantResponse> variantController;
    private GenericController<ImageRequest, ImageResponse> imageController;
    private GenericController<ProductInventoryLimitRequest, ProductInventoryLimitResponse> productInventoryLimitController;
    private GenericController<VariantInventoryLimitRequest, VariantInventoryLimitResponse> variantInventoryLimitController;
    private GenericController<WarehouseRequest, WarehouseResponse> warehouseController;
    private GenericController<CountRequest, CountResponse> countController;
    private GenericController<DestinationRequest, DestinationResponse> destinationController;
    private GenericController<DocketReasonRequest, DocketReasonResponse> docketReasonController;
    private GenericController<TransferRequest, TransferResponse> transferController;
    private GenericController<DocketRequest, DocketResponse> docketController;
    private GenericController<StorageLocationRequest, StorageLocationResponse> storageLocationController;
    private GenericController<PurchaseOrderRequest, PurchaseOrderResponse> purchaseOrderController;
    private GenericController<OrderResourceRequest, OrderResourceResponse> orderResourceController;
    private GenericController<OrderCancellationReasonRequest, OrderCancellationReasonResponse> orderCancellationReasonController;
    private GenericController<OrderRequest, OrderResponse> orderController;
    private GenericController<WaybillRequest, WaybillResponse> waybillController;
    private GenericController<ReviewRequest, ReviewResponse> reviewController;
    private GenericController<PaymentMethodRequest, PaymentMethodResponse> paymentMethodController;
    private GenericController<PromotionRequest, PromotionResponse> promotionController;
    private GenericController<RoomRequest, RoomResponse> roomController;
    private GenericController<RewardStrategyRequest, RewardStrategyResponse> rewardStrategyController;

    private GenericService<District, DistrictRequest, DistrictResponse> districtService;
    private GenericService<Ward, WardRequest, WardResponse> wardService;
    private GenericService<Address, AddressRequest, AddressResponse> addressService;
    private GenericService<User, UserRequest, UserResponse> userService;
    private GenericService<Role, RoleRequest, RoleResponse> roleService;
    private GenericService<Office, OfficeRequest, OfficeResponse> officeService;
    private GenericService<Department, DepartmentRequest, DepartmentResponse> departmentService;
    private GenericService<JobLevel, JobLevelRequest, JobLevelResponse> jobLevelService;
    private GenericService<JobType, JobTypeRequest, JobTypeResponse> jobTypeService;
    private GenericService<JobTitle, JobTitleRequest, JobTitleResponse> jobTitleService;
    private GenericService<Employee, EmployeeRequest, EmployeeResponse> employeeService;
    private GenericService<CustomerGroup, CustomerGroupRequest, CustomerGroupResponse> customerGroupService;
    private GenericService<CustomerResource, CustomerResourceRequest, CustomerResourceResponse> customerResourceService;
    private GenericService<CustomerStatus, CustomerStatusRequest, CustomerStatusResponse> customerStatusService;
    private GenericService<Customer, CustomerRequest, CustomerResponse> customerService;
    private GenericService<Property, PropertyRequest, PropertyResponse> propertyService;
    private GenericService<Category, CategoryRequest, CategoryResponse> categoryService;
    private GenericService<Tag, TagRequest, TagResponse> tagService;
    private GenericService<Guarantee, GuaranteeRequest, GuaranteeResponse> guaranteeService;
    private GenericService<Unit, UnitRequest, UnitResponse> unitService;
    private GenericService<Supplier, SupplierRequest, SupplierResponse> supplierService;
    private GenericService<Brand, BrandRequest, BrandResponse> brandService;
    private GenericService<Specification, SpecificationRequest, SpecificationResponse> specificationService;
    private GenericService<Product, ProductRequest, ProductResponse> productService;
    private GenericService<Variant, VariantRequest, VariantResponse> variantService;
    private GenericService<Image, ImageRequest, ImageResponse> imageService;
    private GenericService<ProductInventoryLimit, ProductInventoryLimitRequest, ProductInventoryLimitResponse> productInventoryLimitService;
    private GenericService<VariantInventoryLimit, VariantInventoryLimitRequest, VariantInventoryLimitResponse> variantInventoryLimitService;
    private GenericService<Warehouse, WarehouseRequest, WarehouseResponse> warehouseService;
    private GenericService<Count, CountRequest, CountResponse> countService;
    private GenericService<Destination, DestinationRequest, DestinationResponse> destinationService;
    private GenericService<DocketReason, DocketReasonRequest, DocketReasonResponse> docketReasonService;
    private GenericService<Transfer, TransferRequest, TransferResponse> transferService;
    private GenericService<StorageLocation, StorageLocationRequest, StorageLocationResponse> storageLocationService;
    private GenericService<PurchaseOrder, PurchaseOrderRequest, PurchaseOrderResponse> purchaseOrderService;
    private GenericService<OrderResource, OrderResourceRequest, OrderResourceResponse> orderResourceService;
    private GenericService<OrderCancellationReason, OrderCancellationReasonRequest, OrderCancellationReasonResponse> orderCancellationReasonService;
    private GenericService<Order, OrderRequest, OrderResponse> orderService;
    private GenericService<PaymentMethod, PaymentMethodRequest, PaymentMethodResponse> paymentMethodService;
    private GenericService<Room, RoomRequest, RoomResponse> roomService;
    private GenericService<RewardStrategy, RewardStrategyRequest, RewardStrategyResponse> rewardStrategyService;

    @PostConstruct
    public void registerControllers() throws NoSuchMethodException {

        register("provinces", provinceController, context.getBean(ProvinceService.class), ProvinceRequest.class);

        register("districts", districtController, districtService.init(
                context.getBean(DistrictRepository.class),
                context.getBean(DistrictMapper.class),
                SearchFieldsConst.DISTRICT,
                ResourceNameConst.DISTRICT
        ), DistrictRequest.class);

        register("wards", wardController, wardService.init(
                context.getBean(WardRepository.class),
                context.getBean(WardMapper.class),
                SearchFieldsConst.WARD,
                ResourceNameConst.WARD
        ), WardRequest.class);

        register("addresses", addressController, addressService.init(
                context.getBean(AddressRepository.class),
                context.getBean(AddressMapper.class),
                SearchFieldsConst.ADDRESS,
                ResourceNameConst.ADDRESS
        ), AddressRequest.class);

        register("users", userController, userService.init(
                context.getBean(UserRepository.class),
                context.getBean(UserMapper.class),
                SearchFieldsConst.USER,
                ResourceNameConst.USER
        ), UserRequest.class);

        register("roles", roleController, roleService.init(
                context.getBean(RoleRepository.class),
                context.getBean(RoleMapper.class),
                SearchFieldsConst.ROLE,
                ResourceNameConst.ROLE
        ), RoleRequest.class);

        register("offices", officeController, officeService.init(
                context.getBean(OfficeRepository.class),
                context.getBean(OfficeMapper.class),
                SearchFieldsConst.OFFICE,
                ResourceNameConst.OFFICE
        ), OfficeRequest.class);

        register("departments", departmentController, departmentService.init(
                context.getBean(DepartmentRepository.class),
                context.getBean(DepartmentMapper.class),
                SearchFieldsConst.DEPARTMENT,
                ResourceNameConst.DEPARTMENT
        ), DepartmentRequest.class);

        register("job-levels", jobLevelController, jobLevelService.init(
                context.getBean(JobLevelRepository.class),
                context.getBean(JobLevelMapper.class),
                SearchFieldsConst.JOB_LEVEL,
                ResourceNameConst.JOB_LEVEL
        ), JobLevelRequest.class);

        register("job-titles", jobTitleController, jobTitleService.init(
                context.getBean(JobTitleRepository.class),
                context.getBean(JobTitleMapper.class),
                SearchFieldsConst.JOB_TITLE,
                ResourceNameConst.JOB_TITLE
        ), JobTitleRequest.class);

        register("job-types", jobTypeController, jobTypeService.init(
                context.getBean(JobTypeRepository.class),
                context.getBean(JobTypeMapper.class),
                SearchFieldsConst.JOB_TYPE,
                ResourceNameConst.JOB_TYPE
        ), JobTypeRequest.class);

        register("employees", employeeController, employeeService.init(
                context.getBean(EmployeeRepository.class),
                context.getBean(EmployeeMapper.class),
                SearchFieldsConst.EMPLOYEE,
                ResourceNameConst.EMPLOYEE
        ), EmployeeRequest.class);

        register("customer-groups", customerGroupController, customerGroupService.init(
                context.getBean(CustomerGroupRepository.class),
                context.getBean(CustomerGroupMapper.class),
                SearchFieldsConst.CUSTOMER_GROUP,
                ResourceNameConst.CUSTOMER_GROUP
        ), CustomerGroupRequest.class);

        register("customer-resources", customerResourceController, customerResourceService.init(
                context.getBean(CustomerResourceRepository.class),
                context.getBean(CustomerResourceMapper.class),
                SearchFieldsConst.CUSTOMER_RESOURCE,
                ResourceNameConst.CUSTOMER_RESOURCE
        ), CustomerResourceRequest.class);

        register("customer-status", customerStatusController, customerStatusService.init(
                context.getBean(CustomerStatusRepository.class),
                context.getBean(CustomerStatusMapper.class),
                SearchFieldsConst.CUSTOMER_STATUS,
                ResourceNameConst.CUSTOMER_STATUS
        ), CustomerStatusRequest.class);

        register("customers", customerController, customerService.init(
                context.getBean(CustomerRepository.class),
                context.getBean(CustomerMapper.class),
                SearchFieldsConst.CUSTOMER,
                ResourceNameConst.CUSTOMER
        ), CustomerRequest.class);

        register("properties", propertyController, propertyService.init(
                context.getBean(PropertyRepository.class),
                context.getBean(PropertyMapper.class),
                SearchFieldsConst.PROPERTY,
                ResourceNameConst.PROPERTY
        ), PropertyRequest.class);

        register("categories", categoryController, categoryService.init(
                context.getBean(CategoryRepository.class),
                context.getBean(CategoryMapper.class),
                SearchFieldsConst.CATEGORY,
                ResourceNameConst.CATEGORY
        ), CategoryRequest.class);

        register("tags", tagController, tagService.init(
                context.getBean(TagRepository.class),
                context.getBean(TagMapper.class),
                SearchFieldsConst.TAG,
                ResourceNameConst.TAG
        ), TagRequest.class);

        register("guarantees", guaranteeController, guaranteeService.init(
                context.getBean(GuaranteeRepository.class),
                context.getBean(GuaranteeMapper.class),
                SearchFieldsConst.GUARANTEE,
                ResourceNameConst.GUARANTEE
        ), GuaranteeRequest.class);

        register("units", unitController, unitService.init(
                context.getBean(UnitRepository.class),
                context.getBean(UnitMapper.class),
                SearchFieldsConst.UNIT,
                ResourceNameConst.UNIT
        ), UnitRequest.class);

        register("suppliers", supplierController, supplierService.init(
                context.getBean(SupplierRepository.class),
                context.getBean(SupplierMapper.class),
                SearchFieldsConst.SUPPLIER,
                ResourceNameConst.SUPPLIER
        ), SupplierRequest.class);

        register("brands", brandController, brandService.init(
                context.getBean(BrandRepository.class),
                context.getBean(BrandMapper.class),
                SearchFieldsConst.BRAND,
                ResourceNameConst.BRAND
        ), BrandRequest.class);

        register("specifications", specificationController, specificationService.init(
                context.getBean(SpecificationRepository.class),
                context.getBean(SpecificationMapper.class),
                SearchFieldsConst.SPECIFICATION,
                ResourceNameConst.SPECIFICATION
        ), SpecificationRequest.class);

        register("products", productController, productService.init(
                context.getBean(ProductRepository.class),
                context.getBean(ProductMapper.class),
                SearchFieldsConst.PRODUCT,
                ResourceNameConst.PRODUCT
        ), ProductRequest.class);

        register("variants", variantController, variantService.init(
                context.getBean(VariantRepository.class),
                context.getBean(VariantMapper.class),
                SearchFieldsConst.VARIANT,
                ResourceNameConst.VARIANT
        ), VariantRequest.class);

        register("images", imageController, imageService.init(
                context.getBean(ImageRepository.class),
                context.getBean(ImageMapper.class),
                SearchFieldsConst.IMAGE,
                ResourceNameConst.IMAGE
        ), ImageRequest.class);

        register("product-inventory-limits", productInventoryLimitController, productInventoryLimitService.init(
                context.getBean(ProductInventoryLimitRepository.class),
                context.getBean(ProductInventoryLimitMapper.class),
                SearchFieldsConst.PRODUCT_INVENTORY_LIMIT,
                ResourceNameConst.PRODUCT_INVENTORY_LIMIT
        ), ProductInventoryLimitRequest.class);

        register("variant-inventory-limits", variantInventoryLimitController, variantInventoryLimitService.init(
                context.getBean(VariantInventoryLimitRepository.class),
                context.getBean(VariantInventoryLimitMapper.class),
                SearchFieldsConst.VARIANT_INVENTORY_LIMIT,
                ResourceNameConst.VARIANT_INVENTORY_LIMIT
        ), VariantInventoryLimitRequest.class);

        register("warehouses", warehouseController, warehouseService.init(
                context.getBean(WarehouseRepository.class),
                context.getBean(WarehouseMapper.class),
                SearchFieldsConst.WAREHOUSE,
                ResourceNameConst.WAREHOUSE
        ), WarehouseRequest.class);

        register("counts", countController, countService.init(
                context.getBean(CountRepository.class),
                context.getBean(CountMapper.class),
                SearchFieldsConst.COUNT,
                ResourceNameConst.COUNT
        ), CountRequest.class);

        register("destinations", destinationController, destinationService.init(
                context.getBean(DestinationRepository.class),
                context.getBean(DestinationMapper.class),
                SearchFieldsConst.DESTINATION,
                ResourceNameConst.DESTINATION
        ), DestinationRequest.class);

        register("docket-reasons", docketReasonController, docketReasonService.init(
                context.getBean(DocketReasonRepository.class),
                context.getBean(DocketReasonMapper.class),
                SearchFieldsConst.DOCKET_REASON,
                ResourceNameConst.DOCKET_REASON
        ), DocketReasonRequest.class);

        register("transfers", transferController, transferService.init(
                context.getBean(TransferRepository.class),
                context.getBean(TransferMapper.class),
                SearchFieldsConst.TRANSFER,
                ResourceNameConst.TRANSFER
        ), TransferRequest.class);

        register("dockets", docketController, context.getBean(DocketService.class), DocketRequest.class);

        register("storage-locations", storageLocationController, storageLocationService.init(
                context.getBean(StorageLocationRepository.class),
                context.getBean(StorageLocationMapper.class),
                SearchFieldsConst.STORAGE_LOCATION,
                ResourceNameConst.STORAGE_LOCATION
        ), StorageLocationRequest.class);

        register("purchase-orders", purchaseOrderController, purchaseOrderService.init(
                context.getBean(PurchaseOrderRepository.class),
                context.getBean(PurchaseOrderMapper.class),
                SearchFieldsConst.PURCHASE_ORDER,
                ResourceNameConst.PURCHASE_ORDER
        ), PurchaseOrderRequest.class);

        register("order-resources", orderResourceController, orderResourceService.init(
                context.getBean(OrderResourceRepository.class),
                context.getBean(OrderResourceMapper.class),
                SearchFieldsConst.ORDER_RESOURCE,
                ResourceNameConst.ORDER_RESOURCE
        ), OrderResourceRequest.class);

        register("order-cancellation-reasons", orderCancellationReasonController, orderCancellationReasonService.init(
                context.getBean(OrderCancellationReasonRepository.class),
                context.getBean(OrderCancellationReasonMapper.class),
                SearchFieldsConst.ORDER_CANCELLATION_REASON,
                ResourceNameConst.ORDER_CANCELLATION_REASON
        ), OrderCancellationReasonRequest.class);

        register("orders", orderController, orderService.init(
                context.getBean(OrderRepository.class),
                context.getBean(OrderMapper.class),
                SearchFieldsConst.ORDER,
                ResourceNameConst.ORDER
        ), OrderRequest.class);

        register("waybills", waybillController, context.getBean(WaybillService.class), WaybillRequest.class);

        register("reviews", reviewController, context.getBean(ReviewService.class), ReviewRequest.class);

        register("payment-methods", paymentMethodController, paymentMethodService.init(
                context.getBean(PaymentMethodRepository.class),
                context.getBean(PaymentMethodMapper.class),
                SearchFieldsConst.PAYMENT_METHOD,
                ResourceNameConst.PAYMENT_METHOD
        ), PaymentMethodRequest.class);

        register("promotions", promotionController, context.getBean(PromotionService.class), PromotionRequest.class);

        register("rooms", roomController, roomService.init(
                context.getBean(RoomRepository.class),
                context.getBean(RoomMapper.class),
                SearchFieldsConst.ROOM,
                ResourceNameConst.ROOM
        ), RoomRequest.class);

        register("reward-strategies", rewardStrategyController, rewardStrategyService.init(
                context.getBean(RewardStrategyRepository.class),
                context.getBean(RewardStrategyMapper.class),
                SearchFieldsConst.REWARD_STRATEGY,
                ResourceNameConst.REWARD_STRATEGY
        ), RewardStrategyRequest.class);

    }

    private <I, O> void register(String resource,
                                 GenericController<I, O> controller,
                                 CrudService<Long, I, O> service,
                                 Class<I> requestType
    ) throws NoSuchMethodException {
        RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
        options.setPatternParser(new PathPatternParser());

        controller.setCrudService(service);
        controller.setRequestType(requestType);

        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/api/" + resource)
                        .methods(RequestMethod.GET)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("getAllResources", int.class, int.class,
                        String.class, String.class, String.class, boolean.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/api/" + resource + "/{id}")
                        .methods(RequestMethod.GET)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("getResource", Long.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/api/" + resource)
                        .methods(RequestMethod.POST)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("createResource", JsonNode.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/api/" + resource + "/{id}")
                        .methods(RequestMethod.PUT)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("updateResource", Long.class, JsonNode.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/api/" + resource + "/{id}")
                        .methods(RequestMethod.DELETE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("deleteResource", Long.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo.paths("/api/" + resource)
                        .methods(RequestMethod.DELETE)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("deleteResources", List.class)
        );
    }

}
