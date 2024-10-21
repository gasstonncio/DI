import tkinter as tk

def insertar_texto():
    #Metodo para generar mucho texto las veces que haga falta. Es solo un ejemplo
    for i in range(1, 10):
        cuadro_texto.insert(tk.END, f" Esto es el {i} párrafo:\n\nLorem ipsum es un texto falso sin ningún sentido. \nEs una secuencia de palabras latinas que, como están posicionadas, no forman oraciones con sentido completo, sino que dan vida a un texto de prueba útil para llenar espacios que posteriormente serán ocupados de textos ad hoc compuestos por profesionales de la comunicación.\n\n\n")

#Abrimos ventana
root = tk.Tk()
root.title("Ejercicio 10")
root.geometry("600x400")

#Texto donde se visualiza el string en bucle
cuadro_texto=tk.Text(root, wrap='word')
cuadro_texto.grid(row=0,column=0,sticky='nsew')

#Barra scroll vertical
scroll=tk.Scrollbar(root, orient='vertical',command=cuadro_texto.yview)
scroll.grid(row=0, column=1,sticky='ns')
cuadro_texto.config(yscrollcommand=scroll.set)

#Configurar el root para que se ajuste al texto
root.grid_rowconfigure(0, weight=1)
root.grid_columnconfigure(0, weight=1)

#Excribir el texto
insertar_texto()

root.mainloop()