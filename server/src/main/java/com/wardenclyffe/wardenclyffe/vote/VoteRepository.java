package com.wardenclyffe.wardenclyffe.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Transactional(readOnly = true)
    @Query("SELECT v FROM Vote v WHERE v.author.id=:authorId AND v.date=:date")
    Optional<Vote> getTodayAuthorVote(@Param("authorId") Long authorId,
                                      @Param("date")LocalDate date);

    @Query("SELECT v from Vote v WHERE v.author.id=:authorId AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC")
    List<Vote> getVoteBetween(@Param("authorId") Long authorId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
