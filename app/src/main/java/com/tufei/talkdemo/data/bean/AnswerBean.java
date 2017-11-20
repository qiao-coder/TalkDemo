package com.tufei.talkdemo.data.bean;

/**
 * Created by tufei on 2017/6/29.
 */

public class AnswerBean {

    /**
     * answer : {"answerType":"openQA","emotion":"default","question":{"question":"你。","question_ws":"你/NP//"},"text":"当然行！持久坚挺！","topicID":"32184207432859670","type":"T"}
     * man_intv :
     * no_nlu_result : 0
     * operation : ANSWER
     * rc : 0
     * service : openQA
     * status : 0
     * text : 你。
     * uuid : atn00a133ab@chb04f0d6dccc16f2a01
     * sid : sch98ff9700@gz3c3e0d6dccc13c3e00
     */

    private Content answer;
    private String man_intv;
    private int no_nlu_result;
    private String operation;
    private int rc;
    private String service;
    private int status;
    private String text;
    private String uuid;
    private String sid;

    public Content getAnswer() {
        return answer;
    }

    public void setAnswer(Content content) {
        answer = content;
    }

    public String getMan_intv() {
        return man_intv;
    }

    public void setMan_intv(String man_intv) {
        this.man_intv = man_intv;
    }

    public int getNo_nlu_result() {
        return no_nlu_result;
    }

    public void setNo_nlu_result(int no_nlu_result) {
        this.no_nlu_result = no_nlu_result;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public static class Content {
        /**
         * answerType : openQA
         * emotion : default
         * question : {"question":"你。","question_ws":"你/NP//"}
         * text : 当然行！持久坚挺！
         * topicID : 32184207432859670
         * type : T
         */

        private String answerType;
        private String emotion;
        private QuestionBean question;
        private String text;
        private String topicID;
        private String type;

        public String getAnswerType() {
            return answerType;
        }

        public void setAnswerType(String answerType) {
            this.answerType = answerType;
        }

        public String getEmotion() {
            return emotion;
        }

        public void setEmotion(String emotion) {
            this.emotion = emotion;
        }

        public QuestionBean getQuestion() {
            return question;
        }

        public void setQuestion(QuestionBean ques) {
            question = ques;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTopicID() {
            return topicID;
        }

        public void setTopicID(String topicID) {
            this.topicID = topicID;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class QuestionBean {
            /**
             * question : 你。
             * question_ws : 你/NP//
             */

            private String question;
            private String question_ws;

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getQuestion_ws() {
                return question_ws;
            }

            public void setQuestion_ws(String question_ws) {
                this.question_ws = question_ws;
            }
        }
    }
}
