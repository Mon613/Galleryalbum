package org.example.bt_galleryalbum.db;

import org.example.bt_galleryalbum.models.Images;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ImgDAO extends DB{

    Statement statement;
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    public boolean create(Images images){
        try{
            connection = getConnect();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("insert into tb_images(nameImg,folder_name) values (?,?)");
            preparedStatement.setString(1,images.getName());
            preparedStatement.setString(2,images.getFolderName());
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    public ArrayList<Images> list(){
        ArrayList<Images> imgs = new ArrayList<>();
        try{
            connection = getConnect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select* from tb_images");

            while (resultSet.next()){
                Images image = new Images();
                image.setId(resultSet.getInt("id"));
                image.setName(resultSet.getString("nameImg"));
                image.setFolderName(resultSet.getString("folder_name"));
                imgs.add(image);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return imgs;
    }
    public boolean delete(Integer id){
        try{
            connection = getConnect();
            preparedStatement = connection.prepareStatement("delete from tb_images where id=? ;");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Images find(Integer id) {
        Images image = new Images();
        try {
            connection = getConnect();
            preparedStatement = connection.prepareStatement("SELECT * FROM tb_images WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                image.setId(resultSet.getInt("id"));
                image.setName(resultSet.getString("nameImg"));
                image.setFolderName(resultSet.getString("folder_name"));

            }
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
