package org.example.bt_galleryalbum.controllers;

import org.example.bt_galleryalbum.db.ImgDAO;
import org.example.bt_galleryalbum.models.Images;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller("gallery")
@RequestMapping("/album")
public class GalleryController implements ICrud {
    @Value("${UPLOAD_DIR}")
    String UPLOAD_FOLDER;
    private ImgDAO imgDAO = new ImgDAO();

    @Override
    @GetMapping("/upload")
    public String create(Model model) {
        Images images = new Images();
        model.addAttribute("image",images);
        return "addImg";
    }
    @PostMapping("/upload")
    public String create(Images images, @RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        try {
            byte[] byteFile = file.getBytes();
            //lấy ra tháng hiện tại
            String month = new SimpleDateFormat("MM").format(new Date());
            //lay ra nam hien tai
            String year = new SimpleDateFormat("yyyy").format(new Date());
            String folderName = month+"-"+year;
            String uploadFolder = UPLOAD_FOLDER+folderName;
            if (!Files.exists(Path.of(uploadFolder))){
                Files.createDirectories(Path.of(uploadFolder));
            }

            String timeStamps = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String extension = file.getOriginalFilename().split("\\.")[1];
            String name = timeStamps+"."+extension;
            Files.write(Path.of(uploadFolder +"/" + timeStamps+"."+extension),byteFile);
            images.setName(name);
            boolean check = imgDAO.create(images);
            if (check){
                redirectAttributes.addFlashAttribute("messageSuccess","Image create successfully");
            }else {
                redirectAttributes.addFlashAttribute("messageFail","Image create fail");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/album/getAll";
    }

    @Override
    @GetMapping("/getAll")
    public String list(Model model) {
        model.addAttribute("imgs",imgDAO.list());
        return "home";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String delete(Integer id, Images images) {
        boolean check = imgDAO.delete(id);
        Images img = imgDAO.find(id);
        if(check){
            try{
                String month = new SimpleDateFormat("MM").format(new Date());
                //lay ra nam hien tai
                String year = new SimpleDateFormat("yyyy").format(new Date());
                String folderName = month+"-"+year;
                String uploadFolder = UPLOAD_FOLDER+folderName;

                System.out.println(images.getName());
                Files.delete(Path.of(uploadFolder + "/" + img.getName()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "redirect:/album/getAll";
    }
}
