package com.test.kotlin.Other

object.run{
//todo
}

@kotlin.internal.InlineOnly
public inline fun <T, R> T.run(block: T.() -> R): R = block()


//kotlin

fun main(args: Array<String>) {
    val user = User("Kotlin", 1, "1111111")

    val result = user.run {
        println("my name is $name, I am $age years old, my phone number is $phoneNum")
        1000
    }
    println("result: $result")
}

//java

public static final void main(@NotNull String[] args) {
    Intrinsics.checkParameterIsNotNull(args, "args");
    User user = new User("Kotlin", 1, "1111111");
    String var5 = "my name is " + user.getName() + ", I am " + user.getAge() + " years old, my phone number is " + user.getPhoneNum();
    System.out.println(var5);
    int result = 1000;
    String var3 = "result: " + result;
    System.out.println(var3);
}

override fun onBindViewHolder(holder: ViewHolder, position: Int){

    getItem(position)?.run{
        holder.tvNewsTitle.text = StringUtils.trimToEmpty(titleEn)
        holder.tvNewsSummary.text = StringUtils.trimToEmpty(summary)
        holder.tvExtraInf = "难度：$gradeInfo | 单词数：$length | 读后感: $numReviews"
        ...

    }

}










