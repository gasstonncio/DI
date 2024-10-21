import tkinter as tk
from tkinter import messagebox

def salir_menu():
    #Metodo para salir de la ventana cuando se selecciona la opcion de Salir en el menu_archivo
    root.quit()

def mensaje_emergente():
    #Metodo para mostrar un mensaje emergente cuando se selccione la opcion Acerca de en el menu Ayuda
    messagebox.showinfo("Información","Mensaje informativo")

#Abrimos ventana
root=tk.Tk()
root.title("Ejercicio 9")
root.geometry("500x500")

#Creamos menu principal
menu_principal=tk.Menu(root)
root.config(menu=menu_principal)

#Primera cascada del menu: Archivo
menu_archivo= tk.Menu(menu_principal, tearoff=0)
menu_principal.add_cascade(label="Archivo", menu=menu_archivo)
menu_archivo.add_command(label="Abrir")
menu_archivo.add_separator()
menu_archivo.add_command(label="Salir", command=salir_menu)

#Segunda cascada delmanu: Ayuda
menu_ayuda= tk.Menu(menu_principal, tearoff=0)
menu_principal.add_cascade(label="Ayuda", menu=menu_ayuda)
menu_ayuda.add_command(label="Acerca de", command=mensaje_emergente)

root.mainloop()