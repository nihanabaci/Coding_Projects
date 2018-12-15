 -- /Users/Nihan/desktop/IIT/cs495/cs495-spring18-nabaci/src/mps:
 -- total used in directory 16 available 4294010705
 -- drwxr-xr-x  3 Nihan  staff   102 Feb  7 10:43 .
 -- drwxr-xr-x  5 Nihan  staff   170 Feb  7 10:43 ..
 -- -rw-r--r--@ 1 Nihan  staff  6148 Feb  7 10:43 .DS_Store

import Data.Char


cycleN :: Int -> [a] -> [a]
cycleN 1 x= x
cycleN 0 x=[]
cycleN n x = x ++ cycleN (n-1) x

countLessThan :: (Ord a) => a -> [a] -> Int
countLessThan _ [] = 0
countLessThan x (y:ys) = if y < x then (1 + countLessThan x ys) else (0 + countLessThan x ys)

removeAll :: (Eq a) => [a] -> [a] -> [a]
removeAll [] x  = x
removeAll x [] = []
removeAll x (y:ys)  
    | y `elem` x =  removeAll x ys
    | otherwise   = y :  removeAll x ys   


join :: a -> [[a]] -> [a]
join _ [y] = y
join x [] = []
join x (y:ys) =  y ++ [x] ++ join x ys


unzip' :: [(a,b)] -> ([a], [b])
unzip' [] = ([], [])
unzip' ((a,b):xs) = (a:(fst (unzip' xs)), b:(snd (unzip' xs)))


runLengthEncode :: String -> [(Int,Char)]
runLengthEncode []  = []
runLengthEncode (x:xs) = runLengthEncode 1 x xs where
    runLengthEncode n x [] = [(n, x)]
    runLengthEncode n x (y:ys)
        | x == y    = runLengthEncode (n + 1) y ys
        | otherwise = (n, x) : runLengthEncode 1 y ys
        

runLengthDecode :: [(Int,Char)] -> String
runLengthDecode [] = []
runLengthDecode ((a,b):xs) = replicate a b ++ runLengthDecode xs


vigenere :: String -> String -> String
vigenere x [] = []
vigenere [] x = x
vigenere x(y:ys)
    | (jj < 26) = [ summ]  ++ vigenere (tail g)  ys
    | otherwise = [ (['A'..'Z'] !! (jj - 26))] ++ vigenere (tail g) ys
     where ff = if y `elem` ['.', ',', '?', '!', ':', ';', ' '] then ([' '] ++ x) else x
           g = cycleN (rem (length ff) (length(y:ys))+1) (ff)
           ux = toUpper (head g)
           uy = toUpper y    
           jj =  (ord(ux)-65 +ord(uy)-65)
           summ = if (jj < 0) || (jj == 3) then chr(ord(uy)) else (['A'..'Z'] !! jj)        
 

