package ins.aifactory.service.qna;

import ins.aifactory.service.task.Task;
import ins.core.entity.EntityInfo;

public class Qna extends EntityInfo {
	
	private Task task;
	private Long qnaSn;
	private String qnaSj;
	private String qnaCn;
	private String qnaAnswer;

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Long getQnaSn() {
		return qnaSn;
	}

	public void setQnaSn(Long qnaSn) {
		this.qnaSn = qnaSn;
	}

	public String getQnaSj() {
		return qnaSj;
	}

	public void setQnaSj(String qnaSj) {
		this.qnaSj = qnaSj;
	}

	public String getQnaCn() {
		return qnaCn;
	}

	public void setQnaCn(String qnaCn) {
		this.qnaCn = qnaCn;
	}

	public String getQnaAnswer() {
		return qnaAnswer;
	}

	public void setQnaAnswer(String qnaAnswer) {
		this.qnaAnswer = qnaAnswer;
	}

}
