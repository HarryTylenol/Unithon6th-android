package team.unithon12.unithonteam12.ext

/**
 * Created by baghyeongi on 2018. 1. 27..
 */
val <T : Any> T.TAG get() = this.javaClass.name