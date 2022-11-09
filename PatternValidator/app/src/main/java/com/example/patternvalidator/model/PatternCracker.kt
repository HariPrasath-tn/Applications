package com.example.patternvalidator.model

class PatternCracker {
    companion object{
        private lateinit var positionIndex: IntArray
        private var len = 0
        private var patternHashMap = HashMap<Char, String>()

        // forming a dictionary of all possibilities of the words
        private val _dictionary = ArrayList<String>()

        /*
         * method to form the dictionary
         *
         * input    ==> String sentence           (ab)
         * output   ==> void
         *
         * iteration:      ==> i = ( 0-> 2-> 3-> .....-> length(sentence) )
         *      iteration:      ==> J = ( 0-> 2-> 3-> .....-> length(sentence) )
         *          subString of sentence           i=0,j=1 ==> subString(0,1) ==> a
         *          append to _dictionary ArrayList
         *
         */
        private fun formDictionary(sentence: String) {
            for (i in sentence.indices) {
                for (j in i + 1 .. sentence.length) {
                    _dictionary.add(sentence.substring(i, j))
                }
            }
        }



        /*
         * method to check whether the pattern match the sentence
         *
         * input ==> array Pattern, string hashMap, string sentence
         * output ==> boolean
         *
         * initializing the empty string "result"
         * methodology:
         *      appending the key's value to the string result
         *
         * if result == sentence:
         *      returns true
         * else:
         *      returns false
         */
        private fun matchPattern(pattern: CharArray, sentence: String): Boolean {
            var result: String? = ""
            for (key in pattern) {
                result += patternHashMap[key]
            }
            return result == sentence
        }



        /*
         * method to try different combinations of the subString
         *
         * input ==> String sentence, String pattern
         * output ==> boolean
         *
         * methodology:
         *      assigning pattern length to the length variable
         *      forming dictionary pattern
         *      iteration:      ==> {0,0,0,1}-> ... {0,0,0,4}->{0,0,1,0}-> ... {0,0,1,4}->{0,0,2,0} .... {4,4,4,4}
         *          calling method @Method assignCurrentValuesToCharacterHashMap()
         *          to assign values to respective hashmap key's
         *
         *          trying to match pattern in each iterations
         *          if pattern matches:
         *              returns true
         *
         *          updating patternArray in each iteration for different patterns
         *
         * else finally ==> returns false
         *
         */
        fun beginCracking(pattern: String, sentence: String): Boolean {
            print("\n\n\n${pattern}   $sentence")
            try {
                patternHashMap = HashMap();
                positionIndex = IntArray(pattern.length)
                len = pattern.length
                formDictionary(sentence)
                println(_dictionary)
                while (positionIndex[0] != _dictionary.size) {
                    assignCurrentValuesToCharacterHashMap(pattern.toCharArray())
                    if (matchPattern(pattern.toCharArray(), sentence)) {
                        return true
                    }
                    updateIndexArray()
                }
            }catch (ignored:Exception){}
            return false
        }



        /*
         * method to assign the values to the hashmap containing pattern keys
         *
         * input ==> character Array (of pattern)
         * output ==> Unit
         *
         * iteration:   (indexes of the pattern array)
         *      assigning corresponding values in dictionary to the hashmap keys with respect to pattern array
         *      ==> {0,0,0,1} ==> {0,0,0,2}...
         */
        private fun assignCurrentValuesToCharacterHashMap(pattern: CharArray) {
            for (i in pattern.indices) {
                patternHashMap[pattern[i]] = _dictionary[positionIndex[i]]
            }
        }


        /*
         * method to update the pattern index array
         *
         * input ==> Unit
         * output ==> Unit
         *
         * initializing count variable as 1
         *
         * iteration while true:
         *      increasing the integer in positionIndexArray[len-count] by 1
         *      if positionIndex[len-count] < _dictionary's size:       (dic size = 4) & position[len-1] = 1,2,3 in positionIndex ={0,0,0,1}
         *          stop's method
         *      assigning positionIndex[len-count] as 0
         *      and incrementing previous index
         *                                                              @Actual concept
         *                                                              if positionIndex[len-1] = 4 which means positionIndex[len-1] will become 0
         *                                                              and positionIndex[len-2] will increase by 1
         *                                                              here {0,0,0,4} ==> {0,0,1,0}
         */
        private fun updateIndexArray() {
            var count = 1
            while (true) {
                positionIndex[len - count] += 1
                if (positionIndex[len - count] < _dictionary.size) {
                    return
                }
                positionIndex[len - count] = 0
                count++
            }
        }
    }
}
