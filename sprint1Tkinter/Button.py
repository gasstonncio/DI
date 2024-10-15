import tkinter as tk
from tkinter import messagebox


def send_message():
    #Metodo para mandar un mensaje en una etiqueta cuando se presione el primer boton
    messagebox.showinfo("Mensaje","Has presionado el primer botón!")

def close():
    #Metodo para cerrar la ventana cuando se presione el segundo boton
    root.destroy()


#Ventana principal

root=tk.Tk()
root.title("Ejercicio 2")
root.geometry("450x200")

#Etiquetas

eb_mensaje=tk.Button(text="Botón reactivo", command=send_message)
eb_mensaje.pack(pady=20)

eb_cerrar=tk.Button(text="Close", command=close, bg="red", fg="black")
eb_cerrar.pack(pady=30)

root.mainloop()