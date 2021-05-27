import java.util


object Main extends App {

    def isAnagram(s: String, t: String): Boolean = {

        if (s.sortWith(_<_) contains t.sortWith(_<_)){ //Here I sort both strings and check whether on string contains another
            true
        }else {
            false
        }

    }

    def canMakeArithmeticProgression(arr: Array[Int]): Boolean = {
        if(arr.length == 2){ //if array has 2 units in array, it automatically can be progression
            true
        }
        else{
            var newArr = arr.sortWith(_<_) // sort array in increasing order
            var diff = newArr(1) - newArr(0)
            for (i <- 1 to newArr.length-1){
                if((newArr(i) - newArr(i-1)) != diff){ // if 2 units with specified indices not equal to difference between first and second units of array, it can't be a progression
                    return false
                }
            }
            true
        }
    }

    def intersection(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {

        var arr = Set[Int]()// here we create set because resulting array should contain unique elements

        for(i <- nums2){
            if(nums1 contains i){ // I check if on array contains elements of other, we add them to the new Set
                arr += i
            }
        }
        arr.toArray
    }

    def canFormArray(arr: Array[Int], pieces: Array[Array[Int]]): Boolean = {
        var mainArr = arr.foldLeft("")((b,a) => b.concat(a.toString).concat(","))  // Here I concatenate elements of array with comma and result is like: [1,2,3] => 1,2,3
        pieces.foldLeft(true)((containsOrNot ,p) => containsOrNot && mainArr.contains(p.foldLeft("")((b,a)=> b.concat(a.toString).concat(",")))) // Here I check whether our "mainArr" string contains separate
                                                                                                                                                        // parts of "pieces" array that are concatenated with the same way

    }



    def largestPerimeter(A: Array[Int]): Int = {
        val arr = A.sorted // sorting of array

        for (i <- (2 to arr.length - 1).reverse) { // reversed loop because we should find the biggest Perimeter, so search from the end of array
            if (arr(i - 1) + arr(i - 2) > arr(i))
                return arr(i - 1) + arr(i - 2) + arr(i) //Here I check if sum of 2 smallest elements is more than the third big one, we can find perimeter, else result is zero
        }
        0
    }

    import java.util

    def maximumUnits(boxTypes: Array[Array[Int]], truckSize: Int) = {
        util.Arrays.sort(boxTypes, (a: Array[Int], b: Array[Int]) => b(1) - a(1)) // sort array with decreasing order


        var tsize = truckSize // our truckSize has val and immutable, so we make it mutable
        var ans = 0
        var i = 0
        while ( {
            tsize > 0 && i < boxTypes.length // tsize every cycle become smaller because each box takes place n truck
        }) {
            if (tsize > boxTypes(i)(0)) {
                ans += (boxTypes(i)(0) * boxTypes(i)(1))
                tsize = tsize - boxTypes(i)(0) // If we have free place in truck, we count units by multiplying boxes on elements in boxes
            }
            else {
                ans += (tsize * boxTypes(i)(1)) // if we don't have free place, just multiply what's left the elements in boxes and our size of truck became zero
                tsize = 0
            }
            i += 1
        }
        ans
    }

    case class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) { // Definition for a binary tree node.
          var value: Int = _value
          var left: TreeNode = _left
          var right: TreeNode = _right
    }

    def maxDepth(root: TreeNode): Int =
        if (root == null) {
            0    //if root value is null then there is no height of the tree
        }

        else {
            1 + Math.max(maxDepth(root.left), maxDepth(root.right)) // Here I recursively calculate height of left and right subtrees of a node and
            // assign height to the node as max of the heights of two children + 1
        }
        
    case class Node(var _value: Int) { // Definition for a binary node.
          var value: Int = _value
          var children: List[Node] = List()
        }

    def maxDepth(root: Node): Int = {
        if (root == null) {
            0
        }
        else {

            var max = 0

            for (n <- root.children) { // For loop in the list which consists of all children of root
                max = Math.max(max, maxDepth(n)) //Here we recursively check for all children of children of root
                //so if it is not leaf and has own children, we add 1 to max value
            }

            1 + max
        }
    }

}