import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Space Probe Mission Control",
  description: "Demonstrating Builder, Prototype & Singleton Design Patterns",
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
