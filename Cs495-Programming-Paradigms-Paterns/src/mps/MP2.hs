import Data.List
import Data.Monoid
import Data.Char

any' :: (a -> Bool) -> [a] -> Bool
any' x ys = foldr ((||) . x) False ys


all' :: (a -> Bool) -> [a] -> Bool
all' x ys =  foldr ((&&) . x) True ys


compose :: [a -> a] -> a -> a
compose fs v = foldl (.) id fs $ v


cycle' :: [a] -> [a]         
cycle' xs = foldr (++) [] (repeat xs)


scanr' :: (a -> b -> b) -> b -> [a] -> [b]
scanr' f zero xs  = foldr w [zero] xs
  where w x xs = (f x (head xs)) : xs


scanl' :: (b -> a -> b) -> b -> [a] -> [b]
scanl' f z xs = reverse $ foldl (\a x -> (f (head a) x): a) [z] xs

inits :: [a] -> [[a]]
inits = foldr ( \ x y -> [x] : (map (x:) y) ) [[]]

tails :: [a] -> [[a]]
tails = foldr ( \ x y ->  reverse([] : reverse(map (x:) y) )) [[]]

minmax :: (Ord a) => [a] -> (a,a)
minmax (x:xs) = foldr (\x (tailMin, tailMax) -> (min x tailMin, max x tailMax)) (x,x) xs

gap :: (Eq a) => a -> a -> [a] ->  Maybe Int
gap z y xs =  elemIndex y g
         where g = drop (head(n)) xs
               n = elemIndices z xs
               -- n = foldl (if z == head(xs) then ((++) 1) else (++) 0) [] tail(xs)
               -- foldl (if z == head(xs) then (++) 1 else (++) 0) [] tail(xs)
     -- where elem = reverse . fst . foldl step ([],0) where
       --     step (is,i) e = (if e == elem then i:is else is, succ i)



words' :: String -> [String]
words' xs = (\ (w, ws) -> if null w then ws else w:ws) $ foldr (\x acc -> if isSpace x then 
             if null (fst acc) then acc
             else
                 ([], (fst acc): (snd acc)) 
             else 
                 (x:fst acc, snd acc)   
                  ) ([],[]) xs



dropWhile' :: (a -> Bool) -> [a] -> [a]
dropWhile' f ls = foldr (\a r b -> if b && f a then r True else a:r False) (const []) ls True



   
