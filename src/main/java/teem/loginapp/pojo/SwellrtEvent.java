/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.pojo;

import java.util.List;

/**
 *
 * @author nikos
 */
public class SwellrtEvent {


    /*{"path":"root.needs.12.completionDate",
    "data":{"projId":"local.net/s+hqwFSt0sybA",
            "summaryText":"There are %n% notifications",
            "collapseKey":"applice",
            "context":"needs",
            "style":"inbox",
            "title":"Jewelry",
            "message":"✔ a task 9",
            "notId":"889303311"},
            "waveid":"local.net/s+hqwFSt0sybA"}
     */
    public class EventData {

        private String projId;
        private String summaryText;
        private String collapseKey;
        private String context;
        private String style;
        private String title;
        private String message;
        private String notId;
        private List<String> recipients;

        public EventData() {
        }

        ;
         
        public String getProjId() {
            return projId;
        }

        public void setProjId(String projId) {
            this.projId = projId;
        }

        public String getSummaryText() {
            return summaryText;
        }

        public void setSummaryText(String summaryText) {
            this.summaryText = summaryText;
        }

        public String getCollapseKey() {
            return collapseKey;
        }

        public void setCollapseKey(String collapseKey) {
            this.collapseKey = collapseKey;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getNotId() {
            return notId;
        }

        public void setNotId(String notId) {
            this.notId = notId;
        }

        public List<String> getRecipients() {
            return recipients;
        }

        public void setRecipients(List<String> recipients) {
            this.recipients = recipients;
        }

    }

    private String path;
    private EventData data;
    private String waveid;

    public SwellrtEvent() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public EventData getData() {
        return data;
    }

    public void setData(EventData data) {
        this.data = data;
    }

    public String getWaveid() {
        return waveid;
    }

    public void setWaveid(String waveid) {
        this.waveid = waveid;
    }
}
