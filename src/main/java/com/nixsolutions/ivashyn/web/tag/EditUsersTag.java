package com.nixsolutions.ivashyn.web.tag;

import com.nixsolutions.ivashyn.db.entity.User;
import com.nixsolutions.ivashyn.util.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.util.List;

/**
 * Created by: Dima Ivashyn
 * Date: 19.03.15
 * Time: 12:41
 */
public class EditUsersTag implements Tag {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditUsersTag.class);

    private PageContext pageContext;
    private Tag parentTag;
    private List<User> usersList;

    public EditUsersTag() {
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    @Override
    public void setParent(Tag tag) {
        parentTag = tag;
    }

    @Override
    public Tag getParent() {
        return parentTag;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.println("<table width=\"80%\" border=\"1\" cellspacing=\"0\" cellpadding=\"4\" align=\"center\">");
            out.println("<tr>");
            out.println("<td>Login</td>");
            out.println("<td>First Name</td>");
            out.println("<td>Last Name</td>");
            out.println("<td>Age</td>");
            out.println("<td>Role</td>");
            out.println("<td colspan=\"2\">Actions</td>");
            out.println("</tr>");

            usersList = getUsersList();
            if (usersList != null) {
                for (User user : usersList) {
                    out.println("<tr>");
                    out.println("<td>" + user.getLogin() + "</td>");
                    out.println("<td>" + user.getFirstName() + "</td>");
                    out.println("<td>" + user.getLastName() + "</td>");
                    out.println("<td>" + UserHelper.getAge(user.getBirthday()) + "</td>");
                    out.println("<td>" + user.getRole().getName() + "</td>");
                    out.println("<td colspan=\"2\">" + "<a href=\"editUser?id=" + user.getId() + "\">" + "Edit</a>");
                    out.println("&nbsp");
                    out.println("<a href=\"deleteUser?id=" + user.getId() + "\" onclick=\"return confirm(\'Are you sure?\')\">" + "Delete </a>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
        } catch (IOException e) {
            LOGGER.error("Error creating custom users list tag", e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        parentTag = null;
    }
}
