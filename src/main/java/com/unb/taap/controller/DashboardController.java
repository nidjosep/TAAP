package com.unb.taap.controller;

import com.unb.taap.core.factory.*;
import com.unb.taap.core.iterator.Iterator;
import com.unb.taap.core.iterator.StudentCollection;
import com.unb.taap.core.singleton.TAAPManager;
import com.unb.taap.model.LabSession;
import com.unb.taap.model.Student;
import com.unb.taap.model.TokenValidation;
import com.unb.taap.model.UserType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    @RequestMapping(value = {"/dashboard"}, method = RequestMethod.GET)
    public String createLabSession(HttpSession session, Model model) {
        String token = String.valueOf(session.getAttribute("token"));
        TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(token);

        if (tokenValidation.isValid()) {
            List<Student> students = new ArrayList<>();
            if (UserType.TA.equals(tokenValidation.getUserType())) {
                StudentCollection studentCollection = TAAPManager.getInstance()
                        .getLabSession(tokenValidation.getLabID())
                        .getStudentCollection();
                if (studentCollection != null) {
                    Iterator<Student> iterator = studentCollection.createIterator();
                    while (iterator.hasNext()) {
                        students.add(iterator.next());
                    }
                }
            } else {
                students.add(TAAPManager.getInstance().getLabSession(tokenValidation.getLabID()).getStudent(
                        String.valueOf(session.getAttribute("id"))));
            }

            model.addAttribute("students", students);

        }
        return "dashboard";
    }

    @GetMapping("/generate-grade-report")
    public ResponseEntity<Resource> generateGradeReport(@RequestParam String format,
                                                        HttpSession session) {
        String content = "";
        String token = String.valueOf(session.getAttribute("token"));
        TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(token);

        if (tokenValidation.isValid()) {
            GradeExportFactory gradeExportFactory;
            switch (format) {
                case "json":
                    gradeExportFactory = new JSONGradeExportFactory();
                    break;
                case "PDF":
                    gradeExportFactory = new PDFGradeExportFactory();

                    break;
                default:
                    gradeExportFactory = new CSVGradeExportFactory();
                    break;
            }
            LabSession labSession = TAAPManager.getInstance().getLabSession(tokenValidation.getLabID());
            GradeExporter gradeExporter = gradeExportFactory.createGradeExporter();
            content = gradeExporter.export(labSession.getStudentCollection());
        }
        Resource resource = new ByteArrayResource(content.getBytes(StandardCharsets.UTF_8));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + format)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

}
