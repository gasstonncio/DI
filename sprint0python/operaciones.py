def suma(a, b):
    """
    Función que recibe dos números y realiza una suma
    """
    return a+b
def resta(a, b):
    """
    Función que recibe dos números y realiza una resta
    """
    return a-b
def mult(a, b):
    """
    Función que recibe dos números y realiza una multiplicación
    """
    return a*b
def div(a,b):
    """
    Función que recibe dos números y realiza una división, en este caso el segundo número nunca puede ser igual a 0
    """
    if b==0:
        """
        En el caso de que el segundo número sea igual a 0, el programa devuelve un aviso
        """
        return "Divisor = 0, no es posible realizar la división"
    else:
        return a/b



