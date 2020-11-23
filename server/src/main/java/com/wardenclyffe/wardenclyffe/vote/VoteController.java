package com.wardenclyffe.wardenclyffe.vote;

import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    private static final LocalTime TIME_EXPIRED = LocalTime.of(0, 1);

    @PostMapping("/{authorId}/{postId}")
    public ResponseEntity<Post> vote(@PathVariable Long authorId,
                                     @PathVariable Long postId) {
        boolean acceptVote = LocalTime.now().isBefore(TIME_EXPIRED);
        VoteTo newVote = acceptVote ? voteService.createOrUpdate(authorId, postId)
                : voteService.create(authorId, postId);
        return new ResponseEntity<>(newVote.getVote().getPost(),
                newVote.isCreated() ? HttpStatus.CREATED : (acceptVote ? HttpStatus.OK : HttpStatus.CONFLICT));
    }

    @GetMapping("/{authorId}")
    public Vote getAuthorVoteForDate(
            @PathVariable Long authorId,
            @RequestParam(value = "date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return voteService.getTodayAuthorVote(authorId, date).get();
    }

    @GetMapping("/history/{authorId}")
    public List<Vote> getVotesBetween(
            @PathVariable Long authorId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return voteService.getVoteBetween(authorId, startDate, endDate);
    }

}
