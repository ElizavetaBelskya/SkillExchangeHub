<%@ tag description = "modal" pageEncoding="utf-8"%>
<%@ attribute name="answer"%>
<%@ attribute name="answerTitle"%>

<div class="modal is-active"  id="answer-modal">
    <div class="modal-background"></div>
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title">${answerTitle}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            ${answer}
        </div>
        <div class="modal-footer">
            <button class="button">Close</button>
        </div>
    </div>
    <button class="modal-close is-large" aria-label="close"></button>
</div>