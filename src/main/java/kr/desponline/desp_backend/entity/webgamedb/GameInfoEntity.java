package kr.desponline.desp_backend.entity.webgamedb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter
@Setter
@Qualifier(value = "webgamedbDataSource")
@Table(name = "webgame")
public class GameInfoEntity {

    @Value("${webgame.board.size.row}")
    private int row;
    @Value("${webgame.board.size.column}")
    private int column;

    @Id
    private String nickname;
    private String board_status;
    private int ss;
    private int s;
    private int a;
    private int b;
    private int c;

    @Getter
    @AllArgsConstructor
    public static class ProcessedGameInfoEntity {

        private final String nickname;
        private final String[][] board_status;
        private final int ss;
        private final int s;
        private final int a;
        private final int b;
        private final int c;
    }

    public ProcessedGameInfoEntity toProcessedGameInfoEntity() {
        String[][] boardStatus = new String[row][column];
        IntStream.range(0, row).forEach(i -> {
            int startIndex = i * column;
            boardStatus[i] = this.board_status.substring(startIndex, startIndex + column).split("");
        });
        return new ProcessedGameInfoEntity(this.nickname, boardStatus,
            this.ss, this.s, this.a, this.b, this.c);
    }
}
