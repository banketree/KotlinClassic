package com.test.kotlin.Other

object.apply{
//todo
}

@kotlin.internal.InlineOnly
public inline fun <T> T.apply(block: T.() -> Unit): T { block(); return this }

//kotlin

fun main(args: Array<String>) {
    val user = User("Kotlin", 1, "1111111")

    val result = user.apply {
        println("my name is $name, I am $age years old, my phone number is $phoneNum")
        1000
    }
    println("result: $result")
}

//java

public final class ApplyFunctionKt {
    public static final void main(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        User user = new User("Kotlin", 1, "1111111");
        String var5 = "my name is " + user.getName() + ", I am " + user.getAge() + " years old, my phone number is " + user.getPhoneNum();
        System.out.println(var5);
        String var3 = "result: " + user;
        System.out.println(var3);
    }
}


mSheetDialogView = View.inflate(activity, R.layout.biz_exam_plan_layout_sheet_inner, null).apply{
    course_comment_tv_label.paint.isFakeBoldText = true
    course_comment_tv_score.paint.isFakeBoldText = true
    course_comment_tv_cancel.paint.isFakeBoldText = true
    course_comment_tv_confirm.paint.isFakeBoldText = true
    course_comment_seek_bar.max = 10
    course_comment_seek_bar.progress = 0

}



if (mSectionMetaData == null || mSectionMetaData.questionnaire == null || mSectionMetaData.section == null) {
    return;
}
if (mSectionMetaData.questionnaire.userProject != null) {
    renderAnalysis();
    return;
}
if (mSectionMetaData.section != null && !mSectionMetaData.section.sectionArticles.isEmpty()) {
    fetchQuestionData();
    return;
}


mSectionMetaData?.apply{

//mSectionMetaData不为空的时候操作mSectionMetaData

}?.questionnaire?.apply{

//questionnaire不为空的时候操作questionnaire

}?.section?.apply{

//section不为空的时候操作section

}?.sectionArticle?.apply{

//sectionArticle不为空的时候操作sectionArticle

}

