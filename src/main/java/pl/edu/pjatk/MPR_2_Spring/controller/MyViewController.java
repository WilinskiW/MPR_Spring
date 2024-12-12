package pl.edu.pjatk.MPR_2_Spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public String displayAddForm(@ModelAttribute("") Car car){
        this.carService.add(car);
        return "redirect:/view/all";
    }

    @GetMapping("view/deleteForm")
    public String displayDeleteForm(Model model){
        model.addAttribute("cars", carService.getCarsList());
        model.addAttribute("inputCar", new Car());
        return "deleteForm";
    }

    @PostMapping("deleteForm")
    public String displayDeleteForm(@ModelAttribute("inputCar") Car car){
        this.carService.delete(car.getId());
        return "redirect:/view/all";
    }

    @GetMapping("view/updateForm")
    public String displayUpdateForm(Model model){
        model.addAttribute("cars", carService.getCarsList());
        model.addAttribute("inputCar", new Car());
        return "updateForm";
    }

    @PostMapping("updateForm")
    public String displayUpdateForm(@ModelAttribute("inputCar") Car car){
        this.carService.update(car.getId(), car);
        return "redirect:/view/all";
    }
}
