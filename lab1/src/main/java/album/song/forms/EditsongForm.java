package album.song.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EditsongForm {
    private String title;
    private String singer;
    private String newTitle;
    private String newSinger;
}
