import tkinter as tk
from tkinter import StringVar, Listbox



#Definimos la clase Usuario
class Usuario:
    def __init__(self, nombre, edad, genero):
        self.nombre = nombre
        self.edad = edad
        self.genero = genero

    def __str__(self):
        return f'Nombre: {self.nombre}, Edad: {self.edad}, Género: {self.genero}'

#Lista para almacenar los objetos Usuario
usuarios = []

def guardar_usuario():
    #Recogemos los datos ingresados
    nombre = entry_nombre.get()
    edad = scale_edad.get()
    genero = var_genero.get()

    #Creamos un objeto Usuario con los datos
    usuario = Usuario(nombre, edad, genero)

    #Lo añadimos a la lista de usuarios
    usuarios.append(usuario)

    #Añadimos el usuario al Listbox
    listbox.insert(tk.END, str(usuario))

    #Limpiamos los campos despues de guardar
    entry_nombre.delete(0, tk.END)
    scale_edad.set(0)
    var_genero.set("Otro")

def eliminar_registro():
    selection=listbox.curselection()
    listbox.delete(selection)

def salir():
    root.quit()

#Creamos la ventana principal
root = tk.Tk()
root.title("Ejercicio 12")
root.geometry("400x400")  # Tamaño ajustado para que sea más compacto

#Etiqueta y campo de entrada para el nombre
et_nombre = tk.Label(root, text="Nombre:")
et_nombre.grid(row=0, column=0, padx=10, pady=10, sticky='e')

entry_nombre = tk.Entry(root, width=25)
entry_nombre.grid(row=0, column=1, padx=10, pady=10)

#Etiqueta y Scale para la edad
et_edad = tk.Label(root, text="Edad:")
et_edad.grid(row=1, column=0, padx=10, pady=10, sticky='e')

scale_edad = tk.Scale(root, from_=0, to=100, orient='horizontal')
scale_edad.grid(row=1, column=1, padx=10, pady=10)

#Etiqueta y botones de seleccion de genero
et_genero = tk.Label(root, text="Género:")
et_genero.grid(row=2, column=0, padx=10, pady=10, sticky='e')

var_genero = tk.StringVar()
var_genero.set("Otro")

#Colocamos los botones
frame_genero = tk.Frame(root)
frame_genero.grid(row=2, column=1, padx=10, pady=10)

boton_masculino = tk.Radiobutton(frame_genero, text="Masculino", variable=var_genero, value="M")
boton_masculino.pack(side="left", padx=5)

boton_femenino = tk.Radiobutton(frame_genero, text="Femenino", variable=var_genero, value="F")
boton_femenino.pack(side="left", padx=5)

boton_otro = tk.Radiobutton(frame_genero, text="Otro", variable=var_genero, value="N/D")
boton_otro.pack(side="left", padx=5)

#Boton para guardar el usuario
boton_guardar = tk.Button(root, text="Guardar usuario", command=guardar_usuario)
boton_guardar.grid(row=3, column=0, columnspan=2, padx=10, pady=10)

#Creamos un Frame para el Listbox y el Scrollbar
frame_listbox = tk.Frame(root)
frame_listbox.grid(row=4, column=0, columnspan=2, padx=10, pady=10)

#Creamos el Listbox para mostrar los usuarios guardados
listbox = Listbox(frame_listbox, width=40, height=8)
listbox.pack(side="left", fill="y")

#Creamos el Scrollbar
scrollbar = tk.Scrollbar(frame_listbox, orient='vertical')
scrollbar.pack(side="right", fill="y")

#Vinculamos el Scrollbar al Listbox
listbox.config(yscrollcommand=scrollbar.set)
scrollbar.config(command=listbox.yview)

#Boton para eliminar registro de listbox
boton_eliminar=tk.Button(root, text="Eliminar registro", command=eliminar_registro)
boton_eliminar.grid(row=5, column=0, columnspan=2, padx=10,pady=10)

etiqueta_menu=tk.Label(root, text="Opciones para la lista")
etiqueta_menu.grid(row=6, column=0, columnspan=1)

menu_lista=tk.Menu(root)



#Boton para salir de la ventana principal
boton_salir=tk.Button(root, text="Salir", command=salir)
boton_salir.grid(padx=0, pady=0)



root.mainloop()