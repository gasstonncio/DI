import tkinter as tk

#Funcion boton

def cambiar_texto():

    etiqueta_boton.config(text="Botón cambiado")


# Ventana principal

root = tk.Tk()
root.title("Ejercicio 1")
root.geometry("300x150")


# Etiquetas

etiqueta_bienvenida=tk.Label(root, text= "Bienvenid@!")
etiqueta_bienvenida.pack()
etiqueta_nombre=tk.Label(root, text="Gastón Caramés")
etiqueta_nombre.pack()
etiqueta_boton=tk.Button(root, text="Botón", command=cambiar_texto, bg="blue", fg="white")
etiqueta_boton.pack()

etiqueta_bienvenida.pack()
etiqueta_nombre.pack()


root.mainloop()




