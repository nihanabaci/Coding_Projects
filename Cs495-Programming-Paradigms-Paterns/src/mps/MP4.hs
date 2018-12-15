import Data.Char

--some -> 1 or more -> if empty not ok with it
--many -> 0 or more -> if empty its ok with it


--identidifier, list of parameters, list of local variables, return val

-- type name -> int or char
data Parser a = Parser { parse :: String -> [(a,String)] }

item :: Parser Char
item = Parser $ \inp -> case inp of [] -> []
                                    (x:xs) -> [(x,xs)]

instance Functor Parser where
  fmap f p = Parser $ \inp -> case parse p inp of
                                [] -> []
                                [(v,out)] -> [(f v, out)]

instance Applicative Parser where
  -- pure :: a -> Parser a
  pure v = Parser $ \inp -> [(v, inp)]
  -- <*> :: Parser (a -> b) -> Parser a -> Parser b
  pf <*> px = Parser $ \inp -> case parse pf inp of
                                 [] -> []
                                 [(f,out)] -> parse (fmap f px) out

instance Monad Parser where
  -- (>>=) :: Parser a -> (a -> Parser b) -> Parser b
  p >>= f = Parser $ \inp -> case parse p inp of
                               [] -> []
                               [(v,out)] -> parse (f v) out

class Applicative f => Alternative f where
  empty :: f a
  (<|>) :: f a -> f a -> f a
  many :: f a -> f [a]
  many x = some x <|> pure []
  some :: f a -> f [a]
  some x = pure (:) <*> x <*> many x

instance Alternative Parser where
  empty = Parser $ \inp -> []
  p <|> q = Parser $ \inp -> case parse p inp of
                               [] -> parse q inp
                               res -> res

sat :: (Char -> Bool) -> Parser Char
sat p = do
  x <- item
  if p x then return x else empty

digit :: Parser Char
digit = sat isDigit

lower :: Parser Char
lower = sat isLower

upper :: Parser Char
upper = sat isUpper

letter :: Parser Char
letter = sat isAlphaNum

char :: Char -> Parser Char
char x = sat (==x)

string :: String -> Parser String
string "" = return []
string (x:xs) = do
  char x
  string xs
  return (x:xs)

ident :: Parser String
ident = do
  x <- lower
  xs <- many letter
  return (x:xs)

nat :: Parser Int
nat = do
  x <- some digit
  return (read x)

space :: Parser ()
space = do
  many (sat isSpace)
  return ()

token :: Parser a -> Parser a
token p = do
  space
  v <- p
  space
  return v
  
int :: Parser Int
int = do
  char '-'
  n <- nat
  return (-n)
  <|> nat

identifier :: Parser String
identifier = token ident

natural :: Parser Int
natural = token nat

integer :: Parser Int
integer = token int

symbol :: String -> Parser String
symbol xs = token (string xs)

nats :: Parser [Int]
nats = do
  symbol "["
  n <- natural
  ns <- many (do symbol ","
                 natural)
  symbol "]"
  return (n:ns)

--2) the parser written for the assignment

funcDef :: Parser (String,[String],[String], String)
funcDef = do
  typeName
  name <- identifier
  param <- paramList
  symbol "{"
  locals <- varDecls
  many assignment
  rval <- do symbol "return"
             val <- identifier <|> (fmap show integer)
             symbol ";"
             return val
             <|> return ""
  symbol "}"
  return (name,param,[],rval)


typeName :: Parser String
typeName = symbol "int" <|> symbol "char"

paramList = do
  symbol "("
  typeName
  p <- identifier
  ps <- many (do symbol ","
                 typeName
                 identifier)
  symbol ")"
  return (p:ps)
 <|> do symbol "("
        symbol ")"
        return []

localVars :: Parser [String]
localVars = undefined


assignment :: Parser () -- a parser for a single assignment statement
assignment = do identifier
                symbol "="
                (fmap show integer) <|> identifier
                symbol ";"
                return ()

varDecls :: Parser [String] -- a parser for variable declarations of a given type
varDecls = do typeName
              p <- identifier
              ps <- many (do symbol ","
                             identifier
                              )
              symbol ";"
              xs <- varDecls
              return (p:ps)
              <|> return []

testCase1 = "int foo1() { }"
testCase2 = "int foo2(char param1) { }"
testCase3 = "char foo3(char param1) { \
           \   return param1; \
           \ }"
testCase4 = "char foo4(char param1) { \
           \   return -1; \
           \ }"
testCase5 = "char foo5(char p1, char p2, int p3) { \
           \   return 0; \
           \ }"
testCase6 = "char foo6(char p1, char p2, int p3) { \
           \   char local1; \ 
           \   return local1; \
           \ }"
testCase7 = "char foo7(char p1, char p2, int p3) { \
           \   char l1, l2, l3; \ 
           \   int l4, l5, l6;  \ 
           \   return -1; \
           \ }"
testCase8 = "char foo8(int p1, int p2, int p3) { \
           \   char buf1; \ 
           \   int n, m; \
           \   char buf2; \
           \   n = m; \ 
           \   buf1 = 10; \
           \   buf2 = -20; \
           \   return 100; \
           \ }"

failCase1 = "foo() { }"
failCase2 = "void foo() { }"
failCase3 = "int foo(int) { }"
failCase4 = "int foo(int i,) { }"
failCase5 = "int foo(char i) { \
           \   return i \
           \ }"
failCase6 = "int foo(char i) { \
           \   char l \
           \   return l; \
           \ }"
failCase7 = "int foo(char i) { \
           \   return l; \
           \   char l; \
           \ }"
failCase8 = "int foo(char i) { \
           \   char l; \
           \   return l; \
           \   l = 10; \
           \ }"
failCase9 = "int foo(char i) { \
           \   l = 10; \
           \   char l; \
           \   return l; \
           \ }"
--parse funcDef testCase1
