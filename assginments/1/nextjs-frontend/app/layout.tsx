import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Space Probe Configuration System",
  description: "Demonstrating Builder, Prototype, and Singleton Design Patterns",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="antialiased">
        {children}
      </body>
    </html>
  );
}
