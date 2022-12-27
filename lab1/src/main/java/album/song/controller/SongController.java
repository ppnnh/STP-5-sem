package album.song.controller;

import album.song.forms.EditsongForm;
import lombok.extern.slf4j.Slf4j;

import album.song.forms.SongForm;
import album.song.model.Song;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class SongController {

    private static List<Song> songs = new ArrayList<Song>();

    static {
        songs.add(new Song("VIBE", "LIL PEEP"));
        songs.add(new Song("BROKEN SMILE", "LIL PEEP"));
    }

    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index was called");
        return modelAndView;
    }

    @GetMapping(value = {"/allsongs"})
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("songlist");
        model.addAttribute("songs", songs);
        return modelAndView;
    }

    @GetMapping(value = {"/addsong"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addsong");
        SongForm songform = new SongForm();
        model.addAttribute("songform", songform);
        return modelAndView;
    }

    @PostMapping(value = {"/addsong"})
    public ModelAndView savePerson(Model model, //
                                   @ModelAttribute("songform") SongForm songForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("songList");
        String title = songForm.getTitle();
        String singer = songForm.getSinger();
        if (title != null && title.length() > 0 //
                && singer != null && singer.length() > 0) {
            Song newSong = new Song(title, singer);
            songs.add(newSong);
            model.addAttribute("songs", songs);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addsong");
        return modelAndView;
    }

    //@RequestMapping(value = {"/deletesong"}, method = RequestMethod.GET)
    @GetMapping(value = {"/deletesong"})
    public ModelAndView showDeleteSongPage(Model model){
        ModelAndView modelAndView = new ModelAndView("deletesong");
        SongForm songForm = new SongForm();
        model.addAttribute("songform",songForm);
        log.info("/deletesong GET was called");
        return  modelAndView;
    }

    @PostMapping(value = {"/deletesong"})
    //@RequestMapping(value = {"/deletesong"}, method = RequestMethod.POST)
    public ModelAndView deleteSong(Model model,
                                @ModelAttribute("songForm") SongForm songForm){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("songList");
        String title = songForm.getTitle();
        String singer = songForm.getSinger();
        log.info("/deletesong POST was called");

        if(title!=null && title.length()>0
                && singer != null && singer.length()>0){
            int index = 0;
            for (Song song: songs){
                if(song.getTitle().equals(title) && song.getSinger().equals(singer)){
                    songs.remove(index);
                    break;
                }
                index++;
            }
            model.addAttribute("songs", songs);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("deletesong");
        return  modelAndView;
    }

    @GetMapping(value = {"/editsong"})
    //@RequestMapping(value = {"/editsong"}, method = RequestMethod.GET)
    public ModelAndView showEditSongPage(Model model){
        ModelAndView modelAndView = new ModelAndView("editsong");
        EditsongForm songForm = new EditsongForm();
        model.addAttribute("songForm",songForm);
        log.info("/editsong GET was called");
        return  modelAndView;
    }

    @PostMapping(value = {"/editsong"})
    //@RequestMapping(value = {"/editsong"}, method = RequestMethod.POST)
    public ModelAndView editSong(Model model,
                                   @ModelAttribute("songForm") EditsongForm songForm){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("songlist");
        String title = songForm.getTitle();
        String singer = songForm.getSinger();
        String newTitle = songForm.getNewTitle();
        String newSinger = songForm.getNewSinger();
        log.info("/editsong POST  was called");

        if(title!=null && title.length()>0
                && singer != null && singer.length()>0
                && newTitle != null && newTitle.length()>0
                && newSinger !=null && newSinger.length()>0){
            for (Song song: songs){
                if(song.getTitle().equals(title) && song.getSinger().equals(singer)){
                    song.setTitle(newTitle);
                    song.setSinger(newSinger);
                    model.addAttribute("songs", songs);
                    return modelAndView;
                }
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("editsong");
        return  modelAndView;
    }
}
