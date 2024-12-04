package pl.edu.pjatk.MPR_2_Spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.service.CarService;

@Controller
public class MyViewController {
    private final CarService carService;

    public MyViewController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("view/all")
    public String viewAllCars(Model model){
        model.addAttribute("carList",  carService.getCarsList());
        return "viewAll";
    }

    @GetMapping("view/addForm")
    public String displayAddForm(Model model){
        model.addAttribute("car", new Car());
        return "addForm";
    }

    @PostMapping("addForm")
    public String displayAddForm(@ModelAttribute Car car){
        this.carService.add(car);
        return "redirect:/view/all";
    }
}
