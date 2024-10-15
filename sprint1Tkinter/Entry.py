import tkinter as tk
#from tkinter import messagebox

def greet():
    e_resultado.config(text=f"Hola {entry.get()}!!")
    """
    Si descomentamos la siguiente linea de messagebox... cuando presionemos
    el boton de la ventana nos saltará otra ventana con elmesnaje presonalizado.

    También habría que desomentar el import de messagebox!!!
    """
    #messagebox.showinfo("Saludo", f"Hola {entry.get()}!!")


root=tk.Tk()
root.title("Ejercicio 3")
root.geometry("400x150")

etiqueta=tk.Label(text="Usuario:")
etiqueta.pack()

entry=tk.Entry(root, width=30)
entry.pack()

button=tk.Button(root, text="Saludo", command=greet)
button.pack(pady=15)

e_resultado=tk.Label(text="")
e_resultado.pack()

root.mainloop()