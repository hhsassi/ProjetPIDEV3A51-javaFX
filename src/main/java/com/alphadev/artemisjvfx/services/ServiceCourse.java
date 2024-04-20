package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.Course;
import com.alphadev.artemisjvfx.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCourse {

    private Connection cnx; // Assuming you have a way to establish a connection

    public ServiceCourse() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void addCourse(Course course) {
        String query = "INSERT INTO cours (titre_cours, description_cours, niveau, file, certification_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, course.getTitle());
            pst.setString(2, course.getDescription());
            pst.setString(3, course.getLevel());
            pst.setString(4, course.getFile());
            pst.setInt(5, course.getCertificationId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Course> getAllCoursesWithCertificationName() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.id, c.titre_cours, c.description_cours, c.niveau, c.file, c.certification_id, cert.nom_certif AS certification_name " +
                "FROM cours c " +
                "JOIN certification cert ON c.certification_id = cert.id";

        try (PreparedStatement pst = cnx.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Course course =new Course();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("titre_cours"));
                course.setDescription(rs.getString("description_cours"));
                course.setLevel(rs.getString("niveau"));
                course.setFile(rs.getString("file"));
                course.setCertificationId(rs.getInt("certification_id"));
                course.setCertificationName(rs.getString("certification_name"));
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching courses with certification names: " + e.getMessage());
        }
        return courses;
    }

    public void updateCourse(Course course) {
        String query = "UPDATE cours SET titre_cours = ?, description_cours = ?, niveau = ?, file = ?, certification_id = ? WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, course.getTitle());
            pst.setString(2, course.getDescription());
            pst.setString(3, course.getLevel());
            pst.setString(4, course.getFile());
            pst.setInt(5, course.getCertificationId());
            pst.setInt(6, course.getId());

            int updatedRows = pst.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Course updated successfully.");
            } else {
                System.out.println("No course was updated, check your query or course ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating course: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteCourse(int courseId) {
        String query = "DELETE FROM cours WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, courseId);

            int deletedRows = pst.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("Course deleted successfully.");
            } else {
                System.out.println("No course was deleted, check your query or course ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
            e.printStackTrace();
        }
    }



}
