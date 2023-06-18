package com.learning.employeeservice.service;

import com.learning.employeeservice.dto.AddressResponse;
import com.learning.employeeservice.dto.EmployeeRequest;
import com.learning.employeeservice.dto.EmployeeResponse;
import com.learning.employeeservice.feignclient.AddressFeignClient;
import com.learning.employeeservice.model.Employee;
import com.learning.employeeservice.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    public static final int STATUS_CODE_200 = 200;
    private static final String ADDRESS_APP = "address-app";
    private static final String CONFIG_PATH = "configPath";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressFeignClient addressFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


//    @Autowired
//    public EmployeeService( @Value("${addressService.baseUrl}") String addressServiceBaseUrl, RestTemplateBuilder restTemplateBuilder) {
//        System.out.println("addressServiceBaseUrl" +addressServiceBaseUrl);
//        this.restTemplate = restTemplateBuilder
//                .rootUri(addressServiceBaseUrl)
//                .build();
//    }

    public boolean createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .id(employeeRequest.getId())
                .name(employeeRequest.getName())
                .designation(employeeRequest.getDesignation())
                .companyName(employeeRequest.getCompanyName())
                .build();
        employeeRepository.save(employee);
        return true;
    }

    public Optional<EmployeeResponse> getEmployeeById(String employeeId) {
        Optional<Employee> employeeObject = employeeRepository.findById(employeeId);
        if (employeeObject.isPresent()) {
            Employee employee = employeeObject.get();
            EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
//            ResponseEntity<AddressResponse> addressResponseEntity =  addressFeignClient.getAddressByEmployeeId(employeeId);
//            if (addressResponseEntity.getStatusCode() == HttpStatusCode.valueOf(STATUS_CODE_200)){
//                AddressResponse addressResponse = addressResponseEntity.getBody();
//                employeeResponse.setAddressResponse(addressResponse);
//            }
//            return Optional.of(employeeResponse);
            AddressResponse addressResponseEntity =  getAddressUsingRestTemplateInLoadBalancedWay(employeeId);
            if(Objects.nonNull(addressResponseEntity)) {
                employeeResponse.setAddressResponse(addressResponseEntity);
            }
            return Optional.of(employeeResponse);
        }
        return Optional.empty();
    }

    private AddressResponse getAddressUsingRestTemplate(String employeeId) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(ADDRESS_APP);
        ServiceInstance instance = serviceInstances.get(0);
        String uriOfAddressService = instance.getUri().toString();
        System.out.println("URO of Address Service >>>>>>>>>>>>>>>> "+uriOfAddressService);
        return restTemplate.getForObject(uriOfAddressService+"/address-app/api/address/{employeeId}", AddressResponse.class, employeeId);
    }

    private AddressResponse getAddressUsingRestTemplateInLoadBalancedWay(String employeeId) {
        ServiceInstance instance = loadBalancerClient.choose(ADDRESS_APP);
        String uriOfAddressService = instance.getUri().toString();
        String contextRoot = instance.getMetadata().get(CONFIG_PATH);
        System.out.println("URI of Address Service >>>>>>>>>>>>>>>> "+uriOfAddressService+contextRoot);
        return restTemplate.getForObject(uriOfAddressService+contextRoot+"/address/{employeeId}", AddressResponse.class, employeeId);
    }
}
