package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"net/http"
	"os"
	"os/signal"
	"time"
)

func main() {
	port := 8082
	waitTime := time.Second * 5
	channel := make(chan os.Signal, 1)

	router := mux.NewRouter().StrictSlash(true)
	router.Use(JsonMiddleware)

	router.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)

		response := Response{
			Message: "ok",
		}

		if err := json.NewEncoder(w).Encode(response); err != nil {
			fmt.Println(fmt.Sprintf("error encoding response: %s", err.Error()))
		}
	})

	server := &http.Server{
		Addr:    fmt.Sprintf("0.0.0.0:%d", port),
		Handler: router,
	}

	go func() {
		fmt.Println(fmt.Sprintf("Starting Webserver on port %d", port))

		if err := server.ListenAndServe(); err != nil {
			if err != http.ErrServerClosed {
				fmt.Println(fmt.Sprintf("HTTP server error :: %v", err))
				channel <- os.Interrupt
			}
		}
	}()

	// Send os interrupt signal to channel
	signal.Notify(channel, os.Interrupt)

	// Block until a cancel signal is received
	<-channel

	fmt.Println("Received cancel signal, shutting down...")

	ctx, cancel := context.WithTimeout(context.Background(), waitTime)
	defer cancel()

	if err := server.Shutdown(ctx); err != nil {
		fmt.Println(err)
	}

	os.Exit(0)
}
