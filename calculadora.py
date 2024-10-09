#En la siguiente línea estamos exportando desde otra clase (operaciones.py) las funciones seleccionadas
from operator import truediv
from xmlrpc.client import boolean

import operaciones
from operaciones import suma, resta, mult, div

#A continuación inicializamos una variable respuesta para que se ejecute el bucle mientras la respuesta sea = 0
resp = 0
while resp == 0:

    #Pedimos dos números y los guardamos en dos variables diferentes para poder realiar las operaciones

    print("Teclee dos números: ")
    print("Primer número: ")
    n1 = int(input())
    print("Segundo número")
    n2 = int(input())

    #Imprimimos un menú de opciones para que el usuario elija la operación a realizar y pedimos que escriba la elección
    #por teclado dentro del rango (1-4)

    print("Ahora escoja la operación que desee realizar: \n\n"
          "1. Suma.\n"
          "2. Resta.\n"
          "3. Multiplicación.\n"
          "4. División.\n\n"
          " ¿Opción que desee aplicar?(1-4): ")
    resp = int(input())

    if resp!=1 and resp !=2 and resp !=3 and resp!=4:

        #Controlamos que la opción elegida se encuentra dentro del rango (1-4).

        #Si no lo está, le damos valor 0 a la variable resp para que se pueda seguir ejecutando el bucle while, lanzamos el mensaje correspondiente
        #y rompemos el if mediante el break para que no se siga ejecutando

        print("La opción elegida no existe.")
        break

        #Si la opción está dentro del rango (1-4), se ejecutará la operación según la respuesta elegida

    elif resp == 1:
        #Invocamos la función suma de la clase "operaciones.py"
        print("Resultado:")
        print(operaciones.suma(n1, n2))
    elif resp == 2:
        # Invocamos la función resta de la clase "operaciones.py"
        print("Resultado:")
        print(operaciones.resta(n1, n2))
    elif resp == 3:
        # Invocamos la función mult de la clase "operaciones.py"
        print("Resultado:")
        print(operaciones.mult(n1, n2))
    elif resp == 4:
        # Invocamos la función div de la clase "operaciones.py"
        print("Resultado:")
        print(operaciones.div(n1, n2))

    #Creamos una variable booleana para saber si la respuesta para seguir con el programa es válida y poder ejecutar el bucle
    valido: bool = False
    while valido==False:
        print("Desea realizar otra operación? (s/n)")

        #En la siguiente línea tecleamos la respuesta y la pasamos a minúsculas para tener menos condiciones
        resp = input().lower()

        #Comprobamos la respuesta y ejecutamos en consecuencia
        if resp == "s":
            #En el caso de que la respuesa sea igual a "s" ejecutaremos el programa de nuevo
            resp = 0
            valido=True
        elif resp =="n":
            #En el caso de que la respuesta sea "n" se terminará el programa
            resp = 5
            valido = True
        else:
            #En el caso de que la respuesta no sea ni "s" ni "n", se volverá a preguntar
            print("Sólo se admiten respuestas s o n")
            valido=False
