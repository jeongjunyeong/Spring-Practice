$(function(){
    $("#btnDel").click(function (){
        let answer= confirm("삭제하시겟습니까");
        answer && $("form[name='frmDelete']").submit();
    });
});