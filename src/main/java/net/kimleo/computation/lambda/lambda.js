
function assert(b) {
    if (!b) {
        throw new EvalError();
    }
}

var one = proc => x => proc(x);

var two = proc => x => proc(proc(x));

var three = proc => x => proc(proc(proc(x)));

var zero = proc => x => x;

var toInteger = proc => proc( (x) => x+1 )(0);

assert(toInteger(one) == 1);
assert(toInteger(three) == 3);

var five = proc => x => proc(proc(proc(proc(proc(x)))));

var TRUE = x => y => x;

var FALSE = x => y => y;

var If = b => x => y => b(x)(y);

var toBoolean = proc => If(proc)(true)(false);

assert(toBoolean(TRUE));
assert(!toBoolean(FALSE));

var isZero = n => n((x) => FALSE)(TRUE);

assert(toBoolean(isZero(zero)));

assert(!toBoolean(isZero(five)));

var pair = x => y => f => f(x)(y);

var left = p => p(x => y => x);

var right = p => p(x => y => y);

assert(isZero(left(pair(zero)(one))));

var inc = n => p => x => p(n(p)(x));

assert(toInteger(inc(two)) == 3);

// slide :: (int, int) => (int, int) = (a, b) -> (b, b+1)
var slide = p => pair(right(p))(inc(right(p)));

assert(toInteger(right(slide(pair(zero)(one)))) == 2);

var dec = n => left(n(slide)(pair(zero)(zero)));

assert(toInteger(dec(three)) == 2);

var add = m => n => n(inc)(m);

var subtract = m => n => n(dec)(m);

var multiply = m => n => n(add(m))(zero);

var power = m => n => n(multiply(m))(one);

var ten = multiply(five)(two);

var hundred = multiply(ten)(ten);

var isLessOrEqual = m => n => isZero(subtract(m)(n));

assert(toBoolean(isLessOrEqual(power(two)(two))(hundred)));

var mod = m => n => If(isLessOrEqual(n)(m))(x => mod(subtract(m)(n))(n)(x))(m);

assert(toInteger(mod(three)(two)) == 1);

var Y = f => (x => f(x(x)))(x => f(x(x)));

var Z = f => (x => f(y => x(x)(y)))(x => f(y => x(x)(y)));

var mod2 = Z( f => m => n => If(isLessOrEqual(n)(m))(x => f(subtract(m)(n))(n)(x))(m));

assert(toInteger(mod2(five)(three)) == 2);

var empty = pair(TRUE)(TRUE);

var unshift = l => x => pair(FALSE)(pair(x)(l));

var isEmpty = left;

var first = l => left(right(l));

var rest = l => right(right(l));

assert(toInteger(first(rest(unshift(unshift(unshift(empty)(three))(two))(one)))) == 2);

assert(!isEmpty(unshift(empty)(three)));

var toArray = (proc, limit = null) => type => {
    var array = [];
    while(!toBoolean(isEmpty(proc)) && limit != 0) {
        array.push(first(proc));
        proc = rest(proc);
        if(limit != null) limit--;
    }

    return array.map(type);
};

var range = Z(f => m => n => If(isLessOrEqual(m)(n)) (x => unshift(f(inc(m))(n))(m)(x)) (empty));

assert(toArray(range(one)(five))(toInteger).length == 5);

var fold = Z( f => l => x => g => If(isEmpty(l)) (x) (y => g(f(rest(l))(x)(g))(first(l))(y)));

var map = k => f => fold(k)(empty)(l => x => unshift(l)(f(x)));

var id = x => x;

var toChar = x => "0123456789BFiuz".charAt(toInteger(x));

var B = ten;
var F = inc(B);
var I = inc(F);
var U = inc(I);
var ZED = inc(U);

var FIZZ = unshift(unshift(unshift(unshift(empty)(ZED))(ZED))(I))(F);
var BUZZ = unshift(unshift(unshift(unshift(empty)(ZED))(ZED))(U))(B);
var FIZZBUZZ = unshift(unshift(unshift(unshift(BUZZ)(ZED))(ZED))(I))(F);

var toString = values => toArray(values)(id).map(toChar).join("");

var div = Z( f => m => n => If(isLessOrEqual(n)(m)) (x => inc(f(subtract(m)(n))(n))(x)) (zero) );

var push = l => x => fold(l)(unshift(empty)(x))(unshift);

var toDigits = Z( f => n => push( If(isLessOrEqual(n)(dec(ten))) (empty) (x => f(div(n)(ten))(x)))(mod(n)(ten)) );

var fifteen = multiply(three)(five);

var solution = map(range(one)(hundred))(n => If(isZero(mod(n)(fifteen))) (FIZZBUZZ) (If(isZero(mod(n)(three))) (FIZZ) (If(isZero(mod(n)(five))) (BUZZ) (toDigits(n)))));

toArray(solution)(toString).forEach(x => console.log(x));

// ## Advanced techniques

// ### Streams

var zeros = Z(f => unshift(f)(zero));

assert(toInteger(first(rest(rest(rest(zeros))))) == 0);

var upwardsOf = Z(f => n => unshift(x => f(inc(n))(x))(n));

var multipliesOf = m => Z(f => n => unshift(x => f(add(m)(n))(x))(n))(m);

var multiplyStreams = Z(f => k => l => unshift(x => f(rest(k))(rest(l))(x)) (multiply(first(k))(first((l)))));


// ### Avoid Recursions

var mod3 = m => n => m(x => If(isLessOrEqual(n)(x)) (subtract(x)(n)) (x))(m);

var countdown = p => pair(unshift(left(p))(right(p)))(dec(right(p)));

var range2 = m => n => left(inc(subtract(n)(m))(countdown)(pair(empty)(n)));