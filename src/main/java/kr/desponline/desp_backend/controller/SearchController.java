package kr.desponline.desp_backend.controller;

import kr.desponline.desp_backend.dto.PlayerRecordDTO;
import kr.desponline.desp_backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/player/{nickname}")
    public PlayerRecordDTO readPlayerByNickname(@PathVariable String nickname) {
        return searchService.findRecordByNickname(nickname);
    }
}
