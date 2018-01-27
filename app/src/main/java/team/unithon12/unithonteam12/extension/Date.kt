package team.unithon12.unithonteam12.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringByFormat(format: String) =
    SimpleDateFormat(format).format(this)
