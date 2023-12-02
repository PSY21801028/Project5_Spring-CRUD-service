package com.example.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BoardDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String BOARD_INSERT = "insert into BOARD (title, writer, content) values (?,?,?)";
    private final String BOARD_UPDATE = "update BOARD set title=?, writer=?, content=? where seq=?";
    private final String BOARD_DELETE = "delete from BOARD where seq=?";

    private final String BOARD_GET = "select * from BOARD where seq=? ";

    private final String BOARD_LIST = "select * from BOARD order by seq desc";
    public int insertBoard(BoardVO vo){

        return jdbcTemplate.update(BOARD_INSERT,new Object[]{vo.getTitle(),vo.getWriter(),vo.getContent()});
    }

    public int deleteBoard(int id){
        return jdbcTemplate.update(BOARD_DELETE,new Object[]{id});
    }

    public int updateBoard(BoardVO vo){

        return jdbcTemplate.update(BOARD_UPDATE,new Object[]{vo.getTitle(),vo.getWriter(),vo.getContent(), vo.getSeq()});
    }

    public BoardVO getBoard(int seq){
        return jdbcTemplate.queryForObject(BOARD_GET,new Object[]{seq},
                new BeanPropertyRowMapper<BoardVO>(BoardVO.class));
    }

    public List<BoardVO> getBoardList(){
        List<BoardVO> list = jdbcTemplate.query(BOARD_LIST, new RowMapper<BoardVO>() {
            @Override
            public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                BoardVO data = new BoardVO();
                data.setSeq(rs.getInt("seq"));
                data.setTitle(rs.getString("title"));
                data.setContent(rs.getString("Content"));
                data.setRegdate(rs.getDate("regdate"));
                data.setWriter(rs.getString("writer"));
                return data;
            }
        });

        return list;
    }
}
