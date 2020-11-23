package com.wardenclyffe.wardenclyffe.vote;

import com.wardenclyffe.wardenclyffe.author.AuthorRepository;
import com.wardenclyffe.wardenclyffe.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;

    public Optional<Vote> getTodayAuthorVote(Long authorId, LocalDate date) {
        return voteRepository.getTodayAuthorVote(authorId, date);
    }

    @Transactional
    public VoteTo create(Long authorId, Long postId) {
        Vote todayVote = getTodayAuthorVote(authorId, LocalDate.now()).orElse(null);
        if (todayVote != null) {
            throw new DataIntegrityViolationException("");
        }
        Vote newVote = new Vote(authorRepository.getOne(authorId),
                postRepository.getOne(postId),
                LocalDate.now());
        VoteTo voteTo = new VoteTo(newVote, true);
        voteRepository.save(voteTo.getVote());
        return voteTo;
    }

    @Transactional
    public VoteTo createOrUpdate(Long authorId, Long postId) {
        Vote newVote = new Vote(authorRepository.getOne(authorId),
                postRepository.getOne(postId),
                LocalDate.now());
        VoteTo todayVote = voteRepository.getTodayAuthorVote(authorId, LocalDate.now())
                .map(vote -> {
                    vote.setPost(postRepository.getOne(postId));
                    return new VoteTo(vote, false);
                }).orElseGet(() -> new VoteTo(newVote, true));
        voteRepository.save(todayVote.getVote());
        return todayVote;
    }

    public List<Vote> getVoteBetween(Long authorId, LocalDate startDate, LocalDate endDate) {
        return voteRepository.getVoteBetween(authorId, startDate!=null ? startDate : LocalDate.of(1, 1, 1),
                endDate!=null? endDate : LocalDate.of(3000, 1, 1));
    }
}
