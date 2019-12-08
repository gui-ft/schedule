package io.gapps.schedule.resource;

import io.gapps.schedule.error.ResourceNotFoundException;
import io.gapps.schedule.model.Customer;
import io.gapps.schedule.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/customer")
public class CustomerResource {

    private final CustomerRepository cr;
    @Autowired
    public CustomerResource(CustomerRepository cr) {
        this.cr = cr;
    }

    @GetMapping
    //@ApiOperation(value = "Retorna uma lista de clientes")
    public ResponseEntity<?> listAllCustomers(Pageable pageable) {
        return new ResponseEntity<>(cr.findByStatus(true, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //@ApiOperation(value = "Busca um cliente pelo id")
    public ResponseEntity<?> findCustomerById(@PathVariable(value = "id") long id) {
        verifyIfCustomerExists(id);
        Customer customer = cr.findByIdCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/byName/{name}")
    //@ApiOperation(value = "Busca um cliente pelo nome")
    public ResponseEntity<?> findCustomerByName(@PathVariable(value = "name") String name) {
        verifyIfCustomerExists(name);
        Customer customer = cr.findByNameIgnoreCaseContaining(name);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    //@ApiOperation(value = "Cadastra um cliente")
    public ResponseEntity<?> saveCustomer(@RequestBody @Valid Customer customer) {
        customer.setStatus(true);
        cr.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping
    //@ApiOperation(value = "Atualiza um cliente")
    public ResponseEntity<Customer> updateCustomer(@RequestBody @Valid Customer customer) {
        verifyIfCustomerExists(customer.getIdCustomer());
        cr.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping
    //@ApiOperation(value = "Deleta um cliente")
    public ResponseEntity<?> deleteCustomer(@RequestBody Customer customer) {
        verifyIfCustomerExists(customer.getIdCustomer());
        customer.setStatus(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfCustomerExists(Long id){
        if (cr.findByIdCustomer(id) == null)
            throw new ResourceNotFoundException("Customer not found for ID: " + id);
    }

    private void verifyIfCustomerExists(String name){
        if (cr.findByNameIgnoreCaseContaining(name) == null)
            throw new ResourceNotFoundException("Customer not found by name: " + name);
    }
}