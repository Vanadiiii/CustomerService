package ru.imatveev.springcourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.imatveev.springcourse.customer.Customer;
import ru.imatveev.springcourse.customer.CustomerService;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/")
    public ModelAndView home() {
        List<Customer> customerList = customerService.listAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("customerList", customerList);
        return modelAndView;
    }

    @RequestMapping("/new")
    public String newCustomerFrom(Map<String, Object> model) {
        Customer customer = new Customer();
        model.put("customer", customer);
        return "new_customer";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/";
    }

    @RequestMapping("/edit")
    public ModelAndView editCustomerForm(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("edit_customer");
        Customer customer = customerService.get(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @RequestMapping("/delete")
    public String deleteCustomerForm(@RequestParam Long id) {
        customerService.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam String keyword) {
        List<Customer> result = customerService.search(keyword);
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("result", result);
        return modelAndView;
    }
}
