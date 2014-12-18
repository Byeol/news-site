package news.core.controller;

import news.core.domain.Comment;
import news.core.service.CommentService;
import news.util.constants.IConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(IConstants.APPLICATION_PATH)
public class CommentController {

    @Autowired
    private CommentService commentService;

    // GET /comments/:id/edit
    @RequestMapping(
            value = IConstants.COMMENTS_PATH + "/{commentId}" + IConstants.EDIT_ACTION_PATH,
            method = RequestMethod.GET
    )
    public String editComment(
            @PathVariable Integer commentId, Model model) {
        Comment comment = commentService.getCommentById(commentId);

        if (comment == null) {
            model.addAttribute("errorDescription", "Comment not found!");
            return "error";
        }

        model.addAttribute("comment", comment);

        return "editComment";
    }
}
