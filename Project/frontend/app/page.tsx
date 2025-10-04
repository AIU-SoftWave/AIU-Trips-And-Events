'use client';

import Link from 'next/link';
import { motion } from 'framer-motion';
import { useEffect } from 'react';

// @ts-ignore - Lordicon types
export default function Home() {
  useEffect(() => {
    // Load Lordicon script
    const script = document.createElement('script');
    script.src = 'https://cdn.lordicon.com/lordicon.js';
    script.async = true;
    document.head.appendChild(script);

    return () => {
      if (document.head.contains(script)) {
        document.head.removeChild(script);
      }
    };
  }, []);

  return (
    <div className="min-h-[calc(100vh-4rem)] relative overflow-hidden">
      {/* Simple Background */}
      <div className="absolute inset-0 bg-gradient-to-br from-slate-50 to-blue-50">
        {/* Simple Static Shapes */}
        <div className="absolute top-20 left-10 w-96 h-96 bg-blue-200 rounded-full mix-blend-multiply filter blur-xl opacity-20"></div>
        <div className="absolute top-40 right-20 w-80 h-80 bg-slate-200 rounded-full mix-blend-multiply filter blur-xl opacity-20"></div>
        <div className="absolute -bottom-20 left-1/3 w-96 h-96 bg-blue-300 rounded-full mix-blend-multiply filter blur-xl opacity-15"></div>
      </div>

      <div className="relative z-10 min-h-[calc(100vh-4rem)] flex items-center justify-center px-4 py-12">
        <div className="text-center space-y-12 max-w-6xl mx-auto">
          {/* Hero Section */}
          <motion.div 
            className="space-y-8"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 1.2, ease: "easeOut" }}
          >
            {/* Logo/Icon */}
            <motion.div 
              className="mx-auto w-24 h-24 bg-gradient-to-br from-blue-500 to-slate-600 rounded-3xl flex items-center justify-center shadow-2xl"
              initial={{ scale: 0, rotate: -180, opacity: 0 }}
              animate={{ scale: 1, rotate: 0, opacity: 1 }}
              transition={{ 
                duration: 1,
                delay: 0.2,
                ease: "easeOut",
                scale: { type: "spring", stiffness: 200, damping: 15 },
                rotate: { type: "spring", stiffness: 100, damping: 10 }
              }}
              whileHover={{ scale: 1.05, rotate: 2 }}
              whileTap={{ scale: 0.95 }}
            >
              {/* @ts-ignore */}
              <lord-icon
                src="https://cdn.lordicon.com/surcxhka.json"
                trigger="loop"
                delay="1000"
                colors="primary:#ffffff,secondary:#e8e8e8"
                style={{ width: '48px', height: '48px' }}
              />
            </motion.div>

            {/* Main Heading */}
            <motion.div 
              className="space-y-4"
              initial={{ opacity: 0, y: 40 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.8, delay: 0.5, ease: "easeOut" }}
            >
              <motion.h1 
                className="text-6xl md:text-7xl font-bold bg-gradient-to-r from-slate-800 to-blue-600 bg-clip-text text-transparent leading-tight"
                initial={{ opacity: 0, scale: 0.9, y: 20 }}
                animate={{ opacity: 1, scale: 1, y: 0 }}
                transition={{ 
                  duration: 0.8, 
                  delay: 0.7,
                  ease: "easeOut",
                  scale: { type: "spring", stiffness: 100, damping: 15 }
                }}
              >
                AIU Events & Trips
              </motion.h1>
              <motion.p 
                className="text-2xl md:text-3xl font-light text-gray-700 max-w-4xl mx-auto leading-relaxed"
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.8, delay: 0.9, ease: "easeOut" }}
              >
                Discover amazing experiences, connect with fellow students, and create unforgettable memories
              </motion.p>
            </motion.div>

            {/* CTA Buttons */}
            <motion.div 
              className="flex flex-col sm:flex-row gap-6 justify-center items-center"
              initial={{ opacity: 0, y: 30 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.8, delay: 1.1, ease: "easeOut" }}
            >
              <motion.div
                whileHover={{ scale: 1.02, y: -2 }}
                whileTap={{ scale: 0.98 }}
              >
                <Link
                  href="/events"
                  className="bg-blue-600 hover:bg-blue-700 text-white px-10 py-4 rounded-2xl font-semibold text-xl shadow-xl hover:shadow-2xl transition-all duration-300 flex items-center"
                >
                  {/* @ts-ignore */}
                  <lord-icon
                    src="https://cdn.lordicon.com/wmwqvixz.json"
                    trigger="hover"
                    colors="primary:#ffffff,secondary:#e8e8e8"
                    style={{ width: '24px', height: '24px', marginRight: '12px' }}
                  />
                  Explore Events
                </Link>
              </motion.div>
              
              <motion.div
                whileHover={{ scale: 1.02, y: -2 }}
                whileTap={{ scale: 0.98 }}
              >
                <Link
                  href="/register"
                  className="bg-white/80 backdrop-blur-sm text-gray-800 px-10 py-4 rounded-2xl font-semibold text-xl shadow-xl hover:shadow-2xl border border-white/30 hover:bg-white/90 transition-all duration-300 flex items-center"
                >
                  {/* @ts-ignore */}
                  <lord-icon
                    src="https://cdn.lordicon.com/dxjqoygy.json"
                    trigger="hover"
                    colors="primary:#374151,secondary:#6b7280"
                    style={{ width: '24px', height: '24px', marginRight: '12px' }}
                  />
                  Join Community
                </Link>
              </motion.div>
            </motion.div>
          </motion.div>
          
          {/* Features Grid */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mt-20">
            <div className="bg-white/70 backdrop-blur-sm p-8 rounded-3xl shadow-xl border border-white/30 hover:shadow-2xl hover:bg-white/80 transition-all duration-300">
                {/* @ts-ignore */}
                <lord-icon
                  src="https://cdn.lordicon.com/oqdmuxru.json"
                  trigger="loop"
                  delay="1000"
                  colors="primary:#ffffff,secondary:#e8e8e8"
                  style={{ width: '32px', height: '32px' }}
                />
              </div>
              <h3 className="text-2xl font-bold mb-4 text-gray-800">Easy Booking</h3>
              <p className="text-gray-600 text-lg leading-relaxed">Reserve your spot with just a few clicks. Our streamlined booking process makes it simple to join any event.</p>
            </div>

            <div className="bg-white/70 backdrop-blur-sm p-8 rounded-3xl shadow-xl border border-white/30 hover:shadow-2xl hover:bg-white/80 transition-all duration-300">
              <div className="w-16 h-16 mx-auto mb-6 bg-gradient-to-br from-slate-500 to-slate-600 rounded-2xl flex items-center justify-center shadow-lg">
                {/* @ts-ignore */}
                <lord-icon
                  src="https://cdn.lordicon.com/qhviklyi.json"
                  trigger="loop"
                  delay="1200"
                  colors="primary:#ffffff,secondary:#e8e8e8"
                  style={{ width: '32px', height: '32px' }}
                />
              </div>
              <h3 className="text-2xl font-bold mb-4 text-gray-800">QR Code Entry</h3>
              <p className="text-gray-600 text-lg leading-relaxed">Get instant access with your personal QR code. No more waiting in lines or lost tickets.</p>
            </div>

            <div className="bg-white/70 backdrop-blur-sm p-8 rounded-3xl shadow-xl border border-white/30 hover:shadow-2xl hover:bg-white/80 transition-all duration-300">
              <div className="w-16 h-16 mx-auto mb-6 bg-gradient-to-br from-blue-500 to-slate-500 rounded-2xl flex items-center justify-center shadow-lg">
                {/* @ts-ignore */}
                <lord-icon
                  src="https://cdn.lordicon.com/ayhtotha.json"
                  trigger="loop"
                  delay="1400"
                  colors="primary:#ffffff,secondary:#e8e8e8"
                  style={{ width: '32px', height: '32px' }}
                />
              </div>
              <h3 className="text-2xl font-bold mb-4 text-gray-800">Stay Connected</h3>
              <p className="text-gray-600 text-lg leading-relaxed">Receive real-time updates about your bookings, event changes, and exclusive opportunities.</p>
            </div>
          </div>

          {/* Stats Section */}
          <div className="bg-white/60 backdrop-blur-sm rounded-3xl p-8 shadow-xl border border-white/30 mt-16">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-8 text-center">
              <div>
                <div className="text-4xl font-bold bg-gradient-to-r from-blue-600 to-slate-600 bg-clip-text text-transparent">500+</div>
                <div className="text-gray-600 font-semibold mt-2">Events Hosted</div>
              </div>
              <div>
                <div className="text-4xl font-bold bg-gradient-to-r from-slate-600 to-blue-600 bg-clip-text text-transparent">2000+</div>
                <div className="text-gray-600 font-semibold mt-2">Happy Students</div>
              </div>
              <div>
                <div className="text-4xl font-bold bg-gradient-to-r from-blue-600 to-slate-600 bg-clip-text text-transparent">50+</div>
                <div className="text-gray-600 font-semibold mt-2">Destinations</div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  );
}

