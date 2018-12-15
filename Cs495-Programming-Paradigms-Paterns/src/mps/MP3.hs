
data Expr = Val Int | Add Expr Expr
folde :: (Int -> a) -> (a -> a -> a) -> Expr -> a
folde f g (Val a) = f a
folde f g (Add x y) = g (folde f g x) (folde f g y)


eval :: Expr -> Int
eval (Val n)       = n
eval (Add x y) = eval x + eval y


size :: Expr -> Int
size (Val n) = 1
size (Add x y) = size x + size y



data ZipList a = Z [a] deriving Show

instance Functor ZipList where
   -- fmap :: (a -> b) -> ZipList a -> ZipList b
    fmap g (Z xs) = Z (fmap g xs)

instance Applicative ZipList where
  --  pure :: a -> ZipList a
    pure x = Z (repeat x)

   -- (<*>) :: ZipList (a -> b) -> ZipList a -> ZipList b
    (Z gs) <*> (Z xs) = Z [g x | (g,x) <- zip gs xs]



data State s a = State { runState :: s -> (a,s) }

instance Functor (State s) where
   fmap f st = State $ \s -> let (x,s') = runState st s
                             in (f x, s')

instance Applicative (State s) where
   pure x = State $ \s -> (x,s)
   stf <*> stx = State $ \s -> let (f,s') = runState stf s
                                   (x,s'') = runState stx s'
                               in (f x, s'')

instance Monad(State s) where  
    return x = State $ \s -> (x,s)  
    st >>= f = State $ \s -> let (x,s') = runState st s
                             in runState (f x) s'

    
dequeue :: State [a] a
dequeue = State $ \(x:xs) -> (x, xs)


enqueue :: a -> State [a] ()
enqueue x = State $ \xs -> ((), x:xs)

stackArith :: State [a] a
stackArith = do
  a <- dequeue
  enqueue a
  b <- dequeue
  enqueue a
  enqueue b
  dequeue


data Expr1 a = Var1 a | Val1 Int | Add1 (Expr1 a) (Expr1 a) deriving Show

instance Functor Expr1 where
   -- fmap :: (a -> b) -> ZipList a -> ZipList b
    fmap f (Var1 x) = Var1 (f x)
    
    

instance Applicative Expr1 where
  --pure :: a -> ZipList a
    pure x = Var1 x
    
    
instance Monad Expr1 where
  -- Val1 b >>= f = f b
   Var1 a >>= f = f a
   return = pure
   
