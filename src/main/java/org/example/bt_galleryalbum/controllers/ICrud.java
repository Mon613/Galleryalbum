package org.example.bt_galleryalbum.controllers;


import org.example.bt_galleryalbum.models.Images;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ICrud {
    public String create(Model model);
    public String list(Model model);
    @GetMapping("/delete/{id}")
    String delete(@PathVariable(name = "id") Integer id, Images images);
}
