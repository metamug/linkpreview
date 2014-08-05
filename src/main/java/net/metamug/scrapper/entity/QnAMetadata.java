/**
 * ***********************************************************************
 *
 * METAMUG.COM CONFIDENTIAL __________________
 *
 * [2013] - [2014] metamug.com All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains the property of
 * metamug.com and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to metamug.com and its suppliers
 * and may be covered by Indian and Foreign Patents, patents in process, and are
 * protected by trade secret or copyright law. Dissemination of this information
 * or reproduction of this material is strictly forbidden unless prior written
 * permission is obtained from metamug.com
 */
package net.metamug.scrapper.entity;

/**
 *
 * @author deepak
 */
public class QnAMetadata extends WebMetaData{    
    
    private String questionTite;
    private String questionDescription;
    private int questionDate;
    private int questionVotes;
    private int questionViews;
    
    private int answerCount;
    private int bestAnswerVotes;
    private String answer;

    public String getQuestionTite() {
        return questionTite;
    }

    public void setQuestionTite(String questionTite) {
        this.questionTite = questionTite;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public int getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(int questionDate) {
        this.questionDate = questionDate;
    }

    public int getQuestionVotes() {
        return questionVotes;
    }

    public void setQuestionVotes(int questionVotes) {
        this.questionVotes = questionVotes;
    }

    public int getQuestionViews() {
        return questionViews;
    }

    public void setQuestionViews(int questionViews) {
        this.questionViews = questionViews;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getBestAnswerVotes() {
        return bestAnswerVotes;
    }

    public void setBestAnswerVotes(int bestAnswerVotes) {
        this.bestAnswerVotes = bestAnswerVotes;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
}
