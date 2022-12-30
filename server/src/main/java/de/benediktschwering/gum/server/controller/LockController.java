package de.benediktschwering.gum.server.controller;

import de.benediktschwering.gum.server.model.Lock;
import de.benediktschwering.gum.server.repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LockController {

    @Autowired
    private LockRepository repo;

//    @GetMapping("/")
//    public List<Lock> test(
//            @RequestParam("name") String name
//    ) {
//        Lock l = new Lock("jawollo");
//        repo.save(l);
//
//        return repo.searchAllByName(name);
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Lock> byId(
//            @PathVariable("id") String id
//    ) {
//        return repo.findById(id);
//    }

}
