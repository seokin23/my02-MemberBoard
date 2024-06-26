package myproject.memberboard.domain.board.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.Board;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.web.form.BoardForm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository // jpa 에서 발생하는 PersistenceException을 추상화를 통해 DataAccessException으로 변환한다.
@Transactional // 서비스 계층에서 걸어주는게 정석이지만 비지니스로직이 엄청 간단하기때문에 repository에 걸어줌
@RequiredArgsConstructor
public class JpaBoardRepository implements BoardRepository {

    private final EntityManager em;


    @Override
    public void save(Board board) {
        em.persist(board);
    }

    @Override
    public List<Board> findAll() {
        String jpql = "select b from Board b";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);
        return query.getResultList();
    }

    @Override
    public Optional<Board> findById(Long id) {
        Board findBoard = em.find(Board.class, id);
        return Optional.ofNullable(findBoard);
    }

    @Override
    public Optional<Board> findByAuthor(String author) {
        String jpql = "select b from Board b where author = :author";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);
        query.setParameter("author", author);
        Board findBoard = query.getSingleResult();
        return Optional.of(findBoard);
    }

    @Override
    public void update(String author, BoardForm param) {
        Board findBoard = findByAuthor(author).orElseThrow(
                () -> new EntityNotFoundException("Author not found: " + author));
        findBoard.setBoardTypeCode(param.getBoardTypeCode());
        findBoard.setTitle(param.getTitle());
        findBoard.setContents(param.getContents());
    }

    @Override
    public boolean delete(String author) {
        Board deleteBoard = findByAuthor(author).get();
        em.remove(deleteBoard);
        return true;
    }
}
