
package code.lib

object FuncTools {
    
    def untilOK[A](gen: => A, ok: A => Boolean): A = {
        var a: A = gen
        while (! ok(a)) a = gen
        a
    }
    
}

